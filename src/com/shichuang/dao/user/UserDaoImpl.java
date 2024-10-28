package com.shichuang.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.StringUtils;

import com.shichuang.domain.User;
import com.shichuang.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int add(User user) {
        // TODO Auto-generated method stub
        String sql = "insert into smbms_user (userCode,userName,userPassword," +
                "userRole,gender,birthday,phone,address,creationDate,createdBy) " +
                "values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(),
                user.getUserRole(), user.getGender(), user.getBirthday(),
                user.getPhone(), user.getAddress(), user.getCreationDate(), user.getCreatedBy()};
       return template.update(sql, params);
    }

    @Override
    public User getLoginUser(String userCode)
     {
        String sql = "select id,userCode,userName,userPassword,userRole,gender,birthday,phone,address,creationDate,createdBy from smbms_user where userCode=?";
        Object[] params = {userCode};
         User u=null;
        try{
            u=template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), params);
        }catch (Exception ex){
            ex.getStackTrace();
        }

        return u;
    }

    @Override
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize)
    {

        StringBuffer sql = new StringBuffer();
        sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
        List<Object> list = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append(" and u.userName like ?");
            list.add("%" + userName + "%");
        }
        if (userRole > 0) {
            sql.append(" and u.userRole = ?");
            list.add(userRole);
        }
        sql.append(" order by creationDate DESC limit ?,?");
        currentPageNo = (currentPageNo - 1) * pageSize;
        list.add(currentPageNo);
        list.add(pageSize);
        Object[] params = list.toArray();
        System.out.println("sql ----> " + sql.toString());
        return template.query(sql.toString(), new BeanPropertyRowMapper<User>(User.class), params);

    }

    @Override
    public int deleteUserById(Integer delId)
    {
        // TODO Auto-generated method stub
        String sql = "delete from smbms_user where id=?";
        Object[] params = {delId};
      return   template.update(sql, delId);
    }

    @Override
    public User getUserById(String id)
    {
        String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
        Object[] params = {id};
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

    @Override
    public int modify(User user)
    {
        // TODO Auto-generated method stub
        String sql = "update smbms_user set userName=?," +
                "gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id = ? ";
        Object[] params = {user.getUserName(), user.getGender(), user.getBirthday(),
                user.getPhone(), user.getAddress(), user.getUserRole(), user.getModifyBy(),
                user.getModifyDate(), user.getId()};
       return template.update(sql, params);
    }

    @Override
    public int updatePwd(int id, String pwd)
     {
        String sql = "update smbms_user set userPassword= ? where id = ?";
        Object[] params = {pwd, id};
       return template.update(sql, params);
    }

    @Override
    public int getUserCount(String userName, int userRole)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
        List<Object> list = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append(" and u.userName like ?");
            list.add("%" + userName + "%");
        }
        if (userRole > 0) {
            sql.append(" and u.userRole = ?");
            list.add(userRole);
        }
        Object[] params = list.toArray();
        System.out.println("sql ----> " + sql.toString());
        return template.queryForObject(sql.toString(), Integer.class, params);
    }


}
