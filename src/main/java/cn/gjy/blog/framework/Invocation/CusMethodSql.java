package cn.gjy.blog.framework.Invocation;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class CusMethodSql
 * 使用方法来生成sql适合多条件查询
 */
public interface CusMethodSql {
    SqlAndArgs handle(Object... data);

    class SqlAndArgs{
        private String sql;
        private Object[] args;

        private SqlAndArgs(String sql,Object[] args){
            this.sql=sql;
            this.args=args;
        }

        public static SqlAndArgs build(String sql, Object... args) {
            return new SqlAndArgs(sql,args);
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }
}
