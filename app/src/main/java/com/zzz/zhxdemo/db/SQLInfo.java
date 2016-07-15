package com.zzz.zhxdemo.db;

public class SQLInfo {

	private static String TableNames[] = {
			"TBL_PERSONAL_INFO",
			"TBL_RUN_DATA"
		};//表名
		

		private static String FieldNames[][] = {
				{"ID","NAME","REAL_NAME","PWD","SEX","AGE","HEIGHT","WEIGHT","QUESTION_ONE","ANSWER_ONE","QUESTION_TWO","ANSWER_TWO","QUESTION_THREE","ANSWER_THREE"},
				{"ID","NAME","USED_TIME","FINISH_DISTANCE","CALORIES","AVG_SPEED","AVG_INCLINE"}
				
		};//字段名
		
		private static String FieldTypes[][] = {
				{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","TEXT","TEXT","INTEGER","INTEGER","INTEGER","REAL","INTEGER","TEXT","INTEGER","TEXT","INTEGER","TEXT"},
				{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","INTEGER","REAL","INTEGER","REAL","INTEGER"}
		};//字段类型
		
		public SQLInfo() {
			// TODO Auto-generated constructor stub
		}
		
		public static String[] getTableNames() {
			return TableNames;
		}
		
		public static String[][] getFieldNames() {
			return FieldNames;
		}
		
		public static String[][] getFieldTypes() {
			return FieldTypes;
		}
}
