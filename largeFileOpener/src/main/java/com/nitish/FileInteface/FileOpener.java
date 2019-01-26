package com.nitish.FileInteface;


import com.nitish.FileEnum.Status;
public interface FileOpener {
	double CountNoOfLine();
	String ReadFile(Status status);
}
