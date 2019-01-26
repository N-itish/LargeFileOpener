package com.nitish.MainProgram;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.nitish.FileEnum.Status;
import com.nitish.FileInteface.FileOpener;
public class TextOpener implements FileOpener{
	private long filePointer;
	private int BYTE_CHUNK = 1024*1024;
	private long FileByteSize;
	private RandomAccessFile file;
	private byte[] Bytes;
	private String filePath;
	private StringBuilder ConvertBytes;
	public TextOpener(String filePath) {
		this.filePath = filePath;
	}
	public double CountNoOfLine(){
		//checking for the next lines in string builder
		String a[] = ConvertBytes.toString().split("\n"); 
		return a.length;
	}
	public String ReadFile(Status status) {
		FileByteSize = (new File(filePath)).length();
		SetPointer(FileByteSize,status);
		
		//checking if the filePointer is going out of bounds
		if(filePointer == -1)
		{
			
			filePointer = 0;
		}
		else if(filePointer > FileByteSize)
		{
			
			filePointer = FileByteSize - BYTE_CHUNK;
		}
		try {
				ConvertBytes = new StringBuilder();
				Bytes = new  byte[BYTE_CHUNK];
				file = new RandomAccessFile(filePath, "r");
				file.seek(filePointer);
				file.read(Bytes);
				file.close();
				//Converting the read bytes to String
				ConvertBytes.append(new String(Bytes,"UTF-8"));
		}
		catch(IOException ioe)
		{
			ioe.getMessage();
		}
		
		//return ConvertBytes;
		return ConvertBytes.toString();
	}
	
	//Determining where to put the file pointer 
	private void SetPointer(long FileByteSize,Status status) {

		switch (status) {
		case BEGINING:
			filePointer = 0;	//placing the pointer to the begining
			break;
		case END: 
			filePointer = FileByteSize - BYTE_CHUNK;
			break;
		case NEXT:
			filePointer += BYTE_CHUNK;
			break;
		case PREVIOUS:
			filePointer -= BYTE_CHUNK;
			break;
		default:
			filePointer = 0;
			break;
		}
	}


}
