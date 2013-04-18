package src.client;

//import java.sql.ResultSet;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

public interface JDBCConnectable extends RemoteService {
	public List executeQuery(String sql,String username, String password);
}
