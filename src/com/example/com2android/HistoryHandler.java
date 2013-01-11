package com.example.com2android;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import android.util.Log;

public class HistoryHandler {

	int currentIndex;
	LinkedList<String> testList;

	public HistoryHandler(){
		currentIndex = -1;
		testList = new LinkedList<String>();

		populateList();
	}

	public String getStringFromHistory(int location){
		if (location == -1){
			return "Error";
		}
		return testList.get(location);
		
	}

	public int getCurrentIndex(){
		if (currentIndex > testList.size()-1){
			currentIndex = testList.size()-1; 
			return -1;
		}
		else if(currentIndex < 0){
			currentIndex = -1; 
			return -1;
		}

		return currentIndex;

	}

	public void addStringToHistory(String command){
		currentIndex = -1;
		testList.addFirst(command);
		Log.d("history", "adding: " + command + " to list.");
	}

	public void changeIndex(boolean isUp){
		if (isUp){
			currentIndex++;
		}
		else
			currentIndex--;		
	}

	protected LinkedList<String> getWholeList(){
		
		for (String s : testList){
			System.out.println("Listitem: " + s);
		}
		return testList;
	}



	//	public void saveList(int numberOfElements){
	//		FileWriter writer;
	//		try {
	//			writer = new FileWriter("history.txt");
	//			
	//			for (int i = 0 ; i < numberOfElements || i == testList.size()-1 ; i++){
	//				writer.write(testList.get(i) + ",");
	//				Log.d("savefile", "Saving: " + testList.get(i) + ",");
	//			}
	//			
	////			for(String str: testList) {
	////				writer.write(str + "\n");
	////			}
	//			writer.close();
	//			Log.d("History", "everything should have been saved now...");
	//		} 
	//		 catch(FileNotFoundException fN) {
	//			 Log.d("History", "Filen finns inte.");
	//			   fN.printStackTrace();
	//			  }
	//		catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//			Log.d("History", "Something went wrong...");
	//		}
	//		
	//	}



	//	private void createFile(int numberOfItems) {
	//		try {
	//			FileOutputStream fos = openFileOutput("history.txt", Context.MODE_PRIVATE);
	//			LinkedList<String> list = historyHandler.getWholeList();
	//
	//			for (String s : list){
	//				s += ",";
	//				fos.write(s.getBytes());
	//				Log.d("savefile", "Saving: " + s);
	//			}
	//			fos.close();
	//			Log.d("savefile", "Allt är sparat...");
	//		} catch (Exception e) {
	//			Log.d("savefile", "Fel vid sparning");
	//		}
	//	}



	public void save(int maxnumbers) {
		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new FileWriter("commandhistory.txt"));
			for ( int i = 0; i < maxnumbers; i++)
			{      
				writer.write(testList.get(i));
				writer.newLine();
				writer.flush();
				System.out.print("Saving: " + testList.get(i));
			}

		} catch(IOException ex) {
			ex.printStackTrace();
		} finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  
		}
	}





	public void populateList(){

		testList.clear();
		testList.addFirst("Command 0");
		testList.addFirst("Command 1");
		testList.addFirst("Command 2");
		testList.addFirst("Command 3");
	}


}
