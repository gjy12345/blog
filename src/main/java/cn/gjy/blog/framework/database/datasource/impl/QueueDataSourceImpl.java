package cn.gjy.blog.framework.database.datasource.impl;

import cn.gjy.blog.framework.annotation.Component;
import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.database.datasource.QueueDataSource;
import cn.gjy.blog.framework.log.SimpleLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author gujianyang
 * @date 2020/12/1
 */
@Config(QueueDataSource.class)
@Component
public class QueueDataSourceImpl extends BaseQueueDataSourceImpl {

    private LinkedBlockingQueue<Connection> connectionsQueue;

    private static final SimpleLog log=SimpleLog.log(QueueDataSourceImpl.class);

    private int maxSize;
    private int initSize;
    private int testWait;
    private String url;
    private String password;
    private String username;
    private String driver;
    private Integer aliveCount;
    private final Object lock=new Object();
    private Object driverObject;
    private transient boolean isStop=false;

    public QueueDataSourceImpl(){
        init();
    }

    protected void init(){
        try {
            File file=new File(this.getClass().getResource("/db.properties").getFile());
            Properties properties=new Properties();
            properties.load(new FileInputStream(file));
            this.url=properties.getProperty("url");
            this.username=properties.getProperty("username");
            this.password=properties.getProperty("password");
            this.maxSize= Integer.parseInt((String) properties.getOrDefault("maxSize","10"));
            this.initSize= Integer.parseInt((String) properties.getOrDefault("initSize","5"));
            this.driver=properties.getProperty("driver");
            this.testWait= Integer.parseInt((String) properties.getOrDefault("testWait","200"));
            if(driver==null){
                driver=guessDriver(url);
            }
            driverObject=Class.forName(driver).newInstance();
            log.v("初始化线程池");
            initQueue();
            log.v("初始化完毕,连接数:"+this.connectionsQueue.size());
        } catch (IOException | ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initQueue() throws SQLException {
        if(this.initSize>this.maxSize){
            this.initSize=this.maxSize;
        }
        this.connectionsQueue=new LinkedBlockingQueue<>(this.maxSize);
        for (int i = 0; i < this.initSize; i++) {
            this.connectionsQueue.add(createConnection(this.url,this.username,this.password));
        }
        this.aliveCount=this.connectionsQueue.size();
    }

    @Override
    public Connection getConnection() {
        try {
            Connection connection=this.connectionsQueue.poll(1, TimeUnit.SECONDS);
            if(connection==null&&this.aliveCount<this.maxSize){
                while (connection==null){
                    if(this.isStop)
                        return null;
                    try {
                        connection=createConnection(url,username,password);
                        if(connection!=null&&connection.isValid(testWait)){
                            break;
                        }
                    } catch (SQLException sqlException) {
                        log.v(sqlException.getSQLState());
                        sqlException.printStackTrace();
                        Thread.sleep(100);
                    }
                }
            }else {
                while (connection==null){
                    if(this.isStop)
                        return null;
                    connection=this.connectionsQueue.poll(50, TimeUnit.NANOSECONDS);
                    try {
                        if(connection!=null&&!connection.isValid(testWait)){
                            connection=null;
                            synchronized (lock){
                                aliveCount--;
                            }
                            if(this.aliveCount<=0){
                                return getConnection();
                            }
                        }
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            }
            return connection;
        } catch (InterruptedException e) {

        }
        throw new RuntimeException("无法获取数据库连接");
    }

    @Override
    public void releaseConnect(Connection connection) {
        try {
            if(connectionsQueue.size()==maxSize){
                connection.close();
                return;
            }
            if(connection.isValid(testWait)){
                this.connectionsQueue.add(connection);
                log.v("连接有效,放入连接池,当前数量:"+this.connectionsQueue.size());
            }else {
                this.connectionsQueue.add(createConnection(url,username,password));
                log.v("连接无效,创建新的连接,当前数量:"+this.connectionsQueue.size());
            }
        } catch (SQLException sqlException) {
            log.v("连接无效,"+sqlException.getMessage());
            try {
                this.connectionsQueue.add(createConnection(url,username,password));
            } catch (SQLException e) {
                log.v("连接失败,"+sqlException.getMessage());
                e.printStackTrace();
                synchronized (lock){
                    this.aliveCount=this.aliveCount-1;
                }
            }
        }
    }

    @Override
    public void close() {
        Iterator<Connection> iterator = this.connectionsQueue.iterator();
        while (iterator.hasNext()){
            try {
                iterator.next().close();
            } catch (Exception e) {

            }
        }
        this.isStop=true;
        log.v("连接池已关闭.");
    }

    @Override
    public void deregisterDriver() {
        super.deregisterDriver();
        log.v("取消注册驱动.");
        if(driverObject!=null){
            try {
                DriverManager.deregisterDriver((Driver) driverObject);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

}
