package com.hehua.mis;

import org.apache.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author Sam
 * @time 2011-1-13 下午02:40:17
 *
 */
public class NewStubGenerator {

    private static final Logger log = Logger.getLogger(NewStubGenerator.class);

    private static final String module = "stat";

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager
                .getConnection(
                        "jdbc:mysql://10.10.1.200/hehua_stat?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8",
                        "hhstat", "hehuaqinzi123");
    }

    //private static final String superService="com.sankuai.meituan.opt.AbstractService";
    //private static final String superService="com.sankuai.meituan.ad.AbstractAdService";
    //    private static final String superService="com.sankuai.meituan.crm.opt.AbstractService";

    private static final String root = "/Users/hewenjerry/workspace/hehua-work/hehua-mis/src/main/";

    private static final String stubRoot = root + "gen";

    private static final String stubPath = root + "gen/com/hehua";

    private static final String corePath = root + "stub/com/hehua";

    private static final String pack = "com.hehua";

    public static void main(String[] args) throws ClassNotFoundException, SQLException,
            UnsupportedEncodingException, IOException {
        log.info("module:" + module);
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("java.class.path"));
        log.info("path:" + new File(root).getCanonicalPath());
        log.info("start...");
        new NewStubGenerator().generate(module,
                new String[] { "summary_order_day","summary_user_day", "summary_client_day", "summary_trace_day", "summary_item_day" });
        log.info("success...");
    }

    private String convertType(String name, String type) {
        if (type.contains("char") || type.contains("binary") || type.contains("text")) {
            return "String";
        }
        if (type.contains("tinyint(1)")) {
            return "boolean";
        }
        if (type.contains("int")) {
            int length = Integer.parseInt(type.substring(type.indexOf('(') + 1, type.indexOf(')')));

            return length > 10 ? "long" : "int";
        }
        if (type.contains("decimal") || type.contains("double")) {
            return "double";
        }
        if (type.contains("float")) {
            return "float";
        }
        if (type.contains("datetime")) {
            return "Date";
        }
        if (type.contains("timestamp")) {
            return "Date";
        }
        if (type.contains("date")) {
            return "Date";
        }
        if (type.contains("bit(1)")) {
            return "boolean";
        }
        throw new IllegalArgumentException("未处理的数据库类型：" + name + "," + type);
    }

    private void generate(String fore, String[] tables) throws ClassNotFoundException,
            SQLException, UnsupportedEncodingException, IOException {
        Connection con = getConnection();
        StringBuilder sb = new StringBuilder();
        line("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>", 0, sb);
        line("<!--", 0, sb);
        line("由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + "生成，请勿人为进行任何修改！", 1, sb);
        line("-->", 0, sb);
        line("<!DOCTYPE configuration PUBLIC \"-//ibatis.apache.org//DTD Config 3.0//EN\" \"http://ibatis.apache.org/dtd/ibatis-3-config.dtd\">",
                0, sb);
        line("<configuration>", 0, sb);

        //        line("<plugins>",1,sb);
        //        line("<plugin interceptor=\"com.sankuai.meituan.opt.OffsetLimitInterceptor\">",2,sb);
        //        line("<property name=\"dialectClass\" value=\"com.sankuai.meituan.opt.MySQLDialect\"/>",3,sb);
        //        line("</plugin>",2,sb);
        //        line("<plugin interceptor=\"com.sankuai.meituan.opt.DefaultJdbcTypeInterceptor\">",2,sb);
        //        line("</plugin>",2,sb);
        //        line("</plugins>",1,sb);

        line("<mappers>", 1, sb);
        String daoPack = pack + "." + fore + ".dao";
        for (String table : tables) {
            Column[] columns = getColumns(con, table);
            //service'  
            generateServiceI(table, columns, fore, getUniqueKeys(con, table));
            //dao'  
            generateDaoI(table, columns, fore, getUniqueKeys(con, table));
            //sql'
            //            generateSqlI(table,columns,fore);
            //pojo
            generateDomainI(table, columns, fore);

            //service
            generateService(table, columns, fore);
            //dao
            generateDao(table, columns, fore);
            //sql
            generateSql(table, columns, fore);
            //pojo
            generateDomain(table, columns, fore);

            String pojoName = upper(table);

            //            line("<mapper resource=\""+daoPack.replace('.', '/')+"/"+pojoName+"I-sqlmap.xml\"/>",2,sb);
            line("<mapper resource=\"" + daoPack.replace('.', '/') + "/" + pojoName
                    + "-sqlmap.xml\"/>", 2, sb);

        }
        line("</mappers>", 1, sb);

        line("</configuration>", 0, sb);

        checkAndSave(sb, stubRoot + "/sql_map_config_" + fore + "_i.xml", "<!DOCTYPE");

        con.close();
    }

    private void checkAndSave(CharSequence content, String path, String split) throws IOException,
            UnsupportedEncodingException {
        String oldBody = readFile(path);
        if (oldBody != null) {
            oldBody = oldBody.substring(oldBody.indexOf(split));
        }
        if (oldBody == null || !content.toString().endsWith(oldBody)) {
            saveFile(path, content);
        }
    }

    private void generateSql(String table, Column[] columns, String fore)
            throws UnsupportedEncodingException, IOException {
        String daoPath = corePath + "/" + fore + "/dao";
        String daoPack = pack + "." + fore + ".dao";
        String pojoName = upper(table);
        String daoName = pojoName + "DAO";
        if (new File(daoPath + "/" + pojoName + "-sqlmap.xml").exists()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        line("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>", 0, sb);
        line("<!DOCTYPE mapper PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\" \"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">",
                0, sb);
        line("<mapper namespace=\"" + daoPack + "." + daoName + "\">", 0, sb);

        line("<!--", 0, sb);
        line("    由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "生成。",
                0, sb);
        line("-->", 0, sb);
        line("", 0, sb);

        line("</mapper>", 0, sb);

        saveFile(daoPath + "/" + pojoName + "-sqlmap.xml", sb);

    }

    private void generateDao(String table, Column[] columns, String fore)
            throws UnsupportedEncodingException, IOException {
        String daoPath = corePath + "/" + fore + "/dao";
        String daoPack = pack + "." + fore + ".dao";
        String pojoPack = pack + "." + fore + ".domain";
        String pojoName = upper(table);
        String daoIName = pojoName + "DAOI";
        String daoName = pojoName + "DAO";
        if (new File(daoPath + "/" + daoName + ".java").exists()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        line("/*", 0, sb);
        line(" * 由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "生成。", 0,
                sb);
        line(" */", 0, sb);
        line("", 0, sb);

        line("package " + daoPack + ";", 0, sb);
        line("", 0, sb);

        line("import org.apache.ibatis.annotations.*;", 0, sb);
        line("import " + pojoPack + "." + pojoName + ";", 0, sb);
        line("", 0, sb);

        line("@javax.inject.Named", 0, sb);
        line("public interface " + daoName + " extends " + daoIName + "{", 0, sb);

        line("", 1, sb);

        line("}", 0, sb);

        saveFile(daoPath + "/" + daoName + ".java", sb);

    }

    private void generateService(String table, Column[] columns, String fore)
            throws UnsupportedEncodingException, IOException {
        String pojoPack = pack + "." + fore + ".domain";
        String pojoName = upper(table);
        String servicePath = corePath + "/" + fore + "/service";
        String servicePack = pack + "." + fore + ".service";
        String serviceIName = pojoName + "ServiceI";
        String serviceName = pojoName + "Service";
        if (new File(servicePath + "/" + serviceName + ".java").exists()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        line("/*", 0, sb);
        line(" * 由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "生成。", 0,
                sb);
        line(" */", 0, sb);
        line("", 0, sb);

        line("package " + servicePack + ";", 0, sb);
        line("", 0, sb);

        line("import org.apache.log4j.Logger;", 0, sb);
        line("import " + pojoPack + "." + pojoName + ";", 0, sb);
        line("import java.util.*;", 0, sb);
        line("", 0, sb);

        line("@javax.inject.Named", 0, sb);
        line("public class " + serviceName + " extends " + serviceIName + "{", 0, sb);
        line("private static final Logger log = Logger.getLogger(" + serviceName + ".class);", 1,
                sb);

        line("", 0, sb);

        line("}", 0, sb);

        saveFile(servicePath + "/" + serviceName + ".java", sb);
    }

    private void generateServiceI(String table, Column[] columns, String fore,
                                  Map<String, List<Element>> keys) throws UnsupportedEncodingException, IOException {
        String daoPack = pack + "." + fore + ".dao";
        String pojoPack = pack + "." + fore + ".domain";
        String pojoName = upper(table);
        String daoName = pojoName + "DAO";
        String servicePath = stubPath + "/" + fore + "/service";
        String servicePack = pack + "." + fore + ".service";
        String serviceIName = pojoName + "ServiceI";

        StringBuilder sb = new StringBuilder();
        line("/*", 0, sb);
        line(" * 由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + "生成，请勿人为进行任何修改！", 0, sb);
        line(" */", 0, sb);
        line("", 0, sb);

        line("package " + servicePack + ";", 0, sb);
        line("", 0, sb);

        line("import " + pojoPack + "." + pojoName + ";", 0, sb);
        line("import " + daoPack + "." + daoName + ";", 0, sb);
        line("import java.util.*;", 0, sb);
        line("", 0, sb);

        //        line("public class "+serviceIName+" extends "+superService+"<"+daoName+">{",0,sb);
        line("public class " + serviceIName + "{", 0, sb);
        line("", 0, sb);
        line("@javax.inject.Inject", 1, sb);
        line("protected " + daoName + " dao;", 1, sb);
        line("", 0, sb);

        line("public " + pojoName + " get" + pojoName + "ById(long id){", 1, sb);
        line("return dao.get" + pojoName + "ById(id);", 2, sb);
        line("}", 1, sb);
        line("", 1, sb);

        line("public boolean has" + pojoName + "WithId(long id){", 1, sb);
        line("return dao.has" + pojoName + "WithId(id)!=null;", 2, sb);
        line("}", 1, sb);
        line("", 1, sb);

        //===================================
        for (String name : keys.keySet()) {
            List<Element> eles = keys.get(name);
            String sigs = "";
            String paras = "";
            String querys = "";
            int index = 0;
            for (Element ele : eles) {
                if (index > 0) {
                    sigs += ",";
                    paras += ",";
                    querys += ",";
                }
                for (Column col : columns) {
                    if (col.name.equals(ele.name)) {
                        sigs += col.getType() + " " + ele.name;
                        break;
                    }
                }
                paras += ele.name;
                querys += "`" + ele.name + "`=#{" + ele.name + "}";

                index++;
            }

            line("public " + pojoName + " get" + pojoName + "By" + upper(name) + "(" + sigs + "){",
                    1, sb);
            line("return dao.get" + pojoName + "By" + upper(name) + "(" + paras + ");", 2, sb);
            line("}", 1, sb);
            line("", 1, sb);

            line("public boolean has" + pojoName + "With" + upper(name) + "(" + sigs + "){", 1, sb);
            line("return dao.has" + pojoName + "With" + upper(name) + "(" + paras + ")!=null;", 2,
                    sb);
            line("}", 1, sb);
            line("", 1, sb);
        }
        //===================================

        line("public List<" + pojoName + "> getAll" + pojoName + "(){", 1, sb);
        line("return dao.getAll" + pojoName + "();", 2, sb);
        line("}", 1, sb);
        line("", 1, sb);

        line("public boolean create" + pojoName + "(" + pojoName + " ent){", 1, sb);
        line("return dao.create" + pojoName + "(ent)==1;", 2, sb);
        line("}", 1, sb);
        line("", 1, sb);

        line("public boolean update" + pojoName + "ById(" + pojoName + " ent){", 1, sb);
        line("return dao.update" + pojoName + "ById(ent)==1;", 2, sb);
        line("}", 1, sb);
        line("", 1, sb);

        line("public boolean delete" + pojoName + "ById(long id){", 1, sb);
        line("return dao.delete" + pojoName + "ById(id)==1;", 2, sb);
        line("}", 1, sb);
        line("", 1, sb);

        line("}", 0, sb);

        checkAndSave(sb, servicePath + "/" + serviceIName + ".java", "package");
    }

    private void generateDaoI(String table, Column[] columns, String fore,
                              Map<String, List<Element>> keys) throws UnsupportedEncodingException, IOException {
        String daoPath = stubPath + "/" + fore + "/dao";
        String daoPack = pack + "." + fore + ".dao";
        String pojoPack = pack + "." + fore + ".domain";
        String pojoName = upper(table);
        String daoIName = pojoName + "DAOI";

        StringBuilder sb = new StringBuilder();
        line("/*", 0, sb);
        line(" * 由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + "生成，请勿人为进行任何修改！", 0, sb);
        line(" */", 0, sb);
        line("", 0, sb);

        line("package " + daoPack + ";", 0, sb);
        line("", 0, sb);

        line("import org.apache.ibatis.annotations.*;", 0, sb);
        line("import " + pojoPack + "." + pojoName + ";", 0, sb);
        line("import java.util.List;", 0, sb);
        line("", 0, sb);

        line("public interface " + daoIName + "{", 0, sb);

        line("@Select(\"select * from `" + table + "` where id=#{id}\")", 1, sb);
        line(pojoName + " get" + pojoName + "ById(long id);", 1, sb);
        line("", 1, sb);

        line("@Select(\"select 1 from `" + table + "` where id=#{id}\")", 1, sb);
        line("Integer has" + pojoName + "WithId(long id);", 1, sb);
        line("", 1, sb);
        //===================================
        for (String name : keys.keySet()) {
            List<Element> eles = keys.get(name);
            String sigs = "";
            String paras = "";
            String querys = "";
            int index = 0;
            for (Element ele : eles) {
                if (index > 0) {
                    sigs += ",";
                    paras += ",";
                    querys += " and ";
                }
                for (Column col : columns) {
                    if (col.name.equals(ele.name)) {
                        sigs += "@Param(\"" + ele.name + "\")" + col.getType() + " " + ele.name;
                        break;
                    }
                }
                paras += ele.name;
                querys += "`" + ele.name + "`=#{" + ele.name + "}";

                index++;
            }

            line("@Select(\"select * from `" + table + "` where " + querys + "\")", 1, sb);
            line(pojoName + " get" + pojoName + "By" + upper(name) + "(" + sigs + ");", 1, sb);
            line("", 1, sb);

            line("@Select(\"select 1 from `" + table + "` where " + querys + "\")", 1, sb);
            line("Integer has" + pojoName + "With" + upper(name) + "(" + sigs + ");", 1, sb);
            line("", 1, sb);
        }
        //===================================

        line("@Select(\"select * from `" + table + "`\")", 1, sb);
        line("List<" + pojoName + "> getAll" + pojoName + "();", 1, sb);
        line("", 1, sb);

        line("@Insert(\"" + buildInsert(table, columns, fore) + "\")", 1, sb);
        line("@Options(useGeneratedKeys = true,keyProperty=\"id\",keyColumn=\"id\")", 1, sb);
        line("int create" + pojoName + "(" + pojoName + " ent);", 1, sb);
        line("", 1, sb);

        line("@Update(\"" + buildUpdate(table, columns, fore) + "\")", 1, sb);
        line("int update" + pojoName + "ById(" + pojoName + " ent);", 1, sb);
        line("", 1, sb);

        line("@Delete(\"delete from `" + table + "` where id=#{id}\")", 1, sb);
        line("int delete" + pojoName + "ById(long id);", 1, sb);
        line("", 1, sb);

        line("}", 0, sb);

        checkAndSave(sb, daoPath + "/" + daoIName + ".java", "package");

    }

    private String buildInsert(String table, Column[] columns, String fore) {
        String fields = "";
        String values = "";
        String asigns = "";
        int counter = 0;
        for (int i = 0; i < columns.length; i++) {
            if (!"id".equalsIgnoreCase(columns[i].name)) {
                if (counter > 0) {
                    fields += ",";
                    values += ",";
                    asigns += ",";
                }
                fields += "`" + columns[i].name + "`";
                values += "#{" + columns[i].name + "}";
                asigns += "`" + columns[i].name + "`" + "=" + "#{" + columns[i].name + "}";

                counter++;
            }
        }

        return "INSERT INTO `" + table + "`(" + fields + ") VALUES (" + values + ")";
    }

    private String buildUpdate(String table, Column[] columns, String fore) {
        String fields = "";
        String values = "";
        String asigns = "";
        int counter = 0;
        for (int i = 0; i < columns.length; i++) {
            if (!"id".equalsIgnoreCase(columns[i].name)) {
                if (counter > 0) {
                    fields += ",";
                    values += ",";
                    asigns += ",";
                }
                fields += "`" + columns[i].name + "`";
                values += "#{" + columns[i].name + "}";
                asigns += "`" + columns[i].name + "`" + "=" + "#{" + columns[i].name + "}";

                counter++;
            }
        }

        return "update `" + table + "` set " + asigns + " where id=#{id}";
    }

    private void generateSqlI(String table, Column[] columns, String fore)
            throws UnsupportedEncodingException, IOException {
        String daoPath = stubPath + "/" + fore + "/dao";
        String daoPack = pack + "." + fore + ".dao";
        String pojoPack = pack + "." + fore + ".domain";
        String pojoName = upper(table);
        String daoName = pojoName + "DAO";

        StringBuilder sb = new StringBuilder();
        line("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>", 0, sb);
        line("<!DOCTYPE mapper PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\" \"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">",
                0, sb);
        line("<mapper namespace=\"" + daoPack + "." + daoName + "\">", 0, sb);

        line("<!--", 0, sb);
        line("    由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + "生成，请勿人为进行任何修改！", 0, sb);
        line("-->", 0, sb);
        line("", 0, sb);

        String fields = "";
        String values = "";
        String asigns = "";
        int counter = 0;
        for (int i = 0; i < columns.length; i++) {
            if (!"id".equalsIgnoreCase(columns[i].name)) {
                if (counter > 0) {
                    fields += ",";
                    values += ",";
                    asigns += ",";
                }
                fields += "`" + columns[i].name + "`";
                values += "#{" + columns[i].name + "}";
                asigns += "`" + columns[i].name + "`" + "=" + "#{" + columns[i].name + "}";

                counter++;
            }
        }

        line("<insert id=\"create" + pojoName + "\" parameterType=\"" + pojoPack + "." + pojoName
                + "\" useGeneratedKeys=\"true\" keyProperty=\"id\">", 1, sb);
        line("INSERT INTO `" + table + "`(", 2, sb);

        line(fields, 3, sb);

        line(")VALUES(", 2, sb);

        line(values, 3, sb);

        line(")", 2, sb);
        line("</insert>", 1, sb);
        line("", 1, sb);

        line("<update id=\"update" + pojoName + "ById" + "\" parameterType=\"" + pojoPack + "."
                + pojoName + "\">", 1, sb);
        line("update `" + table + "` set", 2, sb);

        line(asigns, 3, sb);

        line("where id=#{id}", 2, sb);
        line("</update>", 1, sb);
        line("", 1, sb);

        line("</mapper>", 0, sb);

        saveFile(daoPath + "/" + pojoName + "I-sqlmap.xml", sb);
    }

    private void generateDomain(String table, Column[] columns, String fore)
            throws UnsupportedEncodingException, IOException {
        String pojoPath = corePath + "/" + fore + "/domain";
        String pojoPack = pack + "." + fore + ".domain";
        String pojoName = upper(table);
        if (new File(pojoPath + "/" + pojoName + ".java").exists()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        line("/*", 0, sb);
        line(" * 由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "生成。", 0,
                sb);
        line(" */", 0, sb);
        line("", 0, sb);

        line("package " + pojoPack + ";", 0, sb);
        line("", 0, sb);
        line("import java.util.*;", 0, sb);
        line("", 0, sb);

        line("public class " + pojoName + " extends " + pojoName + "I{", 0, sb);

        line("", 0, sb);

        line("}", 0, sb);

        saveFile(pojoPath + "/" + pojoName + ".java", sb);
    }

    private void generateDomainI(String table, Column[] columns, String fore)
            throws UnsupportedEncodingException, IOException {
        String pojoPath = stubPath + "/" + fore + "/domain";
        String pojoPack = pack + "." + fore + ".domain";
        String pojoName = upper(table) + "I";

        StringBuilder sb = new StringBuilder();
        line("/*", 0, sb);
        line(" * 由系统于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + "生成，请勿人为进行任何修改！", 0, sb);
        line(" */", 0, sb);
        line("", 0, sb);

        line("package " + pojoPack + ";", 0, sb);
        line("", 0, sb);
        line("import java.util.*;", 0, sb);
        line("", 0, sb);

        line("public class " + pojoName + "{", 0, sb);

        for (Column col : columns) {
            line("protected " + col.getType() + " " + col.name + ";", 1, sb);
        }
        line("", 1, sb);

        for (Column col : columns) {
            line("public " + col.getType() + " get" + upper(col.name) + "(){", 1, sb);
            line("return " + col.name + ";", 2, sb);
            line("}", 1, sb);
            line("public void set" + upper(col.name) + "(" + col.getType() + " " + col.name + "){",
                    1, sb);
            line("this." + col.name + "=" + col.name + ";", 2, sb);
            line("}", 1, sb);
            line("", 1, sb);
        }

        line("}", 0, sb);

        checkAndSave(sb, pojoPath + "/" + pojoName + ".java", "package");
    }

    private String upper(String table) {
        String[] words = table.split("_");
        String re = "";
        for (String word : words) {
            re += Character.toUpperCase(word.charAt(0)) + word.substring(1);
        }
        return re;
    }

    private String readFile(String path) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            return null;
        }
        byte[] bytes = new byte[(int) f.length()];
        InputStream in = new FileInputStream(path);
        int readed = 0;
        while ((readed += in.read(bytes, readed, bytes.length - readed)) < bytes.length);
        in.close();

        return new String(bytes, "utf-8");
    }

    private void saveFile(String path, CharSequence content) throws UnsupportedEncodingException,
            IOException {
        new File(path).getParentFile().mkdirs();
        OutputStream out = new FileOutputStream(path);
        out.write(content.toString().getBytes("utf-8"));
        out.close();
    }

    private void line(String line, int tabs, StringBuilder sb) {
        for (int i = 0; i < tabs; i++) {
            sb.append("    ");
        }
        sb.append(line);
        sb.append('\n');
    }

    private class Column {

        String name;

        String type;

        Column(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getType() {
            return convertType(name, type);
        }
    }

    //show index from aclaction

    private Column[] getColumns(Connection con, String table) throws SQLException {
        PreparedStatement ps = null;
        ResultSet set = null;
        List<Column> fields = new ArrayList<Column>();

        try {
            ps = con.prepareStatement("desc " + table);
            set = ps.executeQuery();

            while (set.next()) {
                fields.add(new Column(set.getString(1), set.getString(2)));
            }
        } finally {
            close(ps, set);
        }

        return fields.toArray(new Column[fields.size()]);
    }

    private Map<String, List<Element>> getUniqueKeys(Connection con, String table)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet set = null;

        Map<String, List<Element>> keys = new HashMap<String, List<Element>>();

        try {
            ps = con.prepareStatement("show index from " + table);
            set = ps.executeQuery();

            while (set.next()) {
                if (set.getInt("Non_unique") == 0
                        && !"PRIMARY".equalsIgnoreCase(set.getString("Key_name"))) {
                    String key = set.getString("Key_name");
                    if (key.startsWith("idx_")) {
                        key = key.substring(4);
                    }
                    List<Element> eles = keys.get(key);
                    if (eles == null) {
                        keys.put(key, eles = new ArrayList<Element>());
                    }
                    eles.add(new Element(set.getString("Column_name"), set.getInt("Seq_in_index")));
                }
            }
        } finally {
            close(ps, set);
        }
        for (List<Element> eles : keys.values()) {
            Collections.sort(eles, new java.util.Comparator<Element>() {

                @Override
                public int compare(Element o1, Element o2) {
                    return o1.index - o2.index;
                }
            });
        }
        return keys;
    }

    private class Element {

        private final String name;

        private final int index;

        Element(String name, int index) {
            this.name = name;
            this.index = index;
        }
    }

    private String[] getTables(Connection con, String fore) throws SQLException {
        PreparedStatement ps = null;
        ResultSet set = null;
        List<String> tables = new ArrayList<String>();
        try {
            ps = con.prepareStatement("show tables");
            set = ps.executeQuery();

            while (set.next()) {
                if (set.getString(1).startsWith(fore)) {
                    tables.add(set.getString(1));
                }
            }
        } finally {
            close(ps, set);
        }
        return tables.toArray(new String[tables.size()]);
    }

    //////////////
    public static void close(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Throwable th) {

        }
    }

    public static void close(Connection con, Statement prepStmt, ResultSet rs) {
        close(rs);
        close(con, prepStmt);
    }

    public static void close(Connection con, Statement prepStmt) {
        close(prepStmt);
        close(con);
    }

    public static void close(Statement prepStmt, ResultSet rs) {
        close(prepStmt);
        close(rs);
    }

    public static void close(Statement prepStmt) {
        try {
            if (prepStmt != null) {
                prepStmt.close();
            }
        } catch (Throwable th) {

        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Throwable th) {

        }
    }
}
