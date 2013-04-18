package src.client;

import java.sql.ResultSet;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface JDBCConnectableAsync {
	public void executeQuery(String sql, String username, String password, AsyncCallback<List> callback);
}
