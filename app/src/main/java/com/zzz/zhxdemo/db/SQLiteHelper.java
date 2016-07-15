package com.zzz.zhxdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/*
* 使用方式
*db =SQLiteHelper.getInstance(getApplicationContext());
*db.open();
* 创建一个db，可以在主界面进行static创建一个db,之后直接调用MainActivity.db即可。
* public static SQLiteHelper db = null;
* */

public class SQLiteHelper {
	
	private SQLDbHelper mDbHelper;    //SQLiteOpenHelper实例对象
    private SQLiteDatabase mDb;    //数据库实例对象
    private static SQLiteHelper openHelper = null;//数据库调用实例
    
	private static int version = 1;//数据库版本号
	  
	private static String myDBName = "mydb";
	private static String TableNames[];//表名
	private static String FieldNames[][];//字段名
	private static String FieldTypes[][];//字段类型
	private static String NO_CREATE_TABLES = "no tables";
	private static String message = "";
	
	private final Context mCtx;    //上下文实例
  
	private SQLiteHelper(Context ctx) {
	        this.mCtx = ctx;
	}
	
	public static SQLiteHelper getInstance(Context context){
		if(openHelper == null){
			openHelper = new SQLiteHelper(context);
			TableNames = SQLInfo.getTableNames();
			FieldNames = SQLInfo.getFieldNames();
			FieldTypes = SQLInfo.getFieldTypes();
		}
		return openHelper;
	}
	

	
	private static class SQLDbHelper extends SQLiteOpenHelper{
		
		public SQLDbHelper(Context context) {
			super(context, myDBName, null, version);
			// TODO Auto-generated constructor stub
		}



		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			if(TableNames == null){
				message = NO_CREATE_TABLES;
				return;
			}
			for(int i = 0; i < TableNames.length; i++){
				String sql = "CREATE TABLE IF NOT EXISTS " + TableNames[i] + " (";
	  	    	for (int j = 0; j < FieldNames[i].length; j++)
	  	    	{
	  	    		sql += FieldNames[i][j] + " " + FieldTypes[i][j] + ",";
	  	    	}
	  	    	sql = sql.substring(0, sql.length() - 1);
	  	    	sql += ")";
	  	    	db.execSQL(sql);
			}
		}
		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			switch(oldVersion){
				case 1:
					break;
				case 2:
					break;
			}
		}
		
		
	}

	/**添加数据库相关信息*/
	public void insertTables(String[] tableNames,String[][] fieldNames,String[][] fieldTypes){
		TableNames = tableNames;
		FieldNames = fieldNames;
		FieldTypes = fieldTypes;
	}

	/**打开数据库*/
	public SQLiteHelper open() throws SQLException {
        mDbHelper = new SQLDbHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }


	/**关闭数据库*/
    public void close() {
        mDbHelper.close();
    }
    
	public void execSQL(String sql) throws java.sql.SQLException
	{
		mDb.execSQL(sql);
	}

	/**sql语句查询数据*/
	public Cursor rawQuery(String sql,String[] selectionArgs)
	{
		Cursor cursor = mDb.rawQuery(sql, selectionArgs);
		return cursor;
	}

	/**查询数据*/
	public Cursor select(String table, String[] columns, 
			String selection, String[] selectionArgs, String groupBy, 
			String having, String orderBy)
	{
		Cursor cursor = mDb.query
		(
				table, columns, selection, selectionArgs, 
				groupBy, having, orderBy
		);
		return cursor;
	}

	/**添加数据*/
	public long insert(String table, String fields[], String values[])
	{
		ContentValues cv = new ContentValues();
		for (int i = 0; i < fields.length; i++)
		{
			cv.put(fields[i], values[i]);
		}
		return mDb.insert(table, null, cv);
	}

	/**删除数据*/
	public int delete(String table, String where, String[] whereValue)
	{
		return mDb.delete(table, where, whereValue);
	}

	/**更新数据*/
	public int update(String table, String updateFields[],
			String updateValues[], String where, String[] whereValue)
	{
		ContentValues cv = new ContentValues();
		for (int i = 0; i < updateFields.length; i++)
		{
			cv.put(updateFields[i], updateValues[i]);
		}
		return mDb.update(table, cv, where, whereValue);
	}

	/**错误信息： 不为null数据库未建立*/
	public String getMessage()
	{
		return message;
	}


	

}
