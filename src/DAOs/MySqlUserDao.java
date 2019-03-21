/**                                             Feb 2019
 * 
 * Data Access Object (DAO) for User table with MySQL-specific code
 * This 'concrete' class implements the 'UserDaoInterface'.
 * 
 * The DAO will contain the SQL query code to interact with the database,
 * so, the code here is specific to a particular database (e.g. MySQL or Oracle etc...)
 * No SQL queries will be used in the Business logic layer of code, thus, it 
 * will be independent of the database specifics.
 * 
*/

package Daos;

import DTOs.User;
import Exceptions.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MySqlUserDao extends MySqlDao implements UserDaoInterface
{
    @Override
    public List<User> findAllUsers() throws DaoException 
    {
    	Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        
        try 
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM USER";
            ps = con.prepareStatement(query);
            
            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next()) 
            {
                int userId = rs.getInt("ID");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String lastname = rs.getString("LAST_NAME");
                String firstname = rs.getString("FIRST_NAME");
                User u = new User(userId, firstname, lastname, username, password);
                users.add(u);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findAllUsers() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return users;     // may be empty
    }

    @Override
    public User findUserByUsernamePassword(String uname, String pword) throws DaoException 
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User u = null;
        try {
            con = this.getConnection();
            
            String query = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, uname);
            ps.setString(2, pword);
            
            rs = ps.executeQuery();
            if (rs.next()) 
            {
            	int userId = rs.getInt("ID");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String lastname = rs.getString("LAST_NAME");
                String firstname = rs.getString("FIRST_NAME");
                u = new User(userId, firstname, lastname, username, password);
            }
        } 
        catch (SQLException e) 
        {
            throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
        } 
        finally 
        {
            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (ps != null) 
                {
                    ps.close();
                }
                if (con != null) 
                {
                    freeConnection(con);
                }
            } 
            catch (SQLException e) 
            {
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return u;     // u may be null 
    }  
}