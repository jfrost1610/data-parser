/**
 * 
 */
package com.frost.dataparser.writer;

import java.io.IOException;

import com.frost.dataparser.model.DocumentDetails;

/**
 * @author jobin
 *
 */
public interface DataWriter {

	void initialize() throws IOException;

	boolean write(DocumentDetails document);

	boolean update(DocumentDetails document);

	String getFilePath();

}
