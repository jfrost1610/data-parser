/**
 * 
 */
package com.frost.dataparser.model;

import java.util.List;

import lombok.Data;

/**
 * @author jobin
 *
 */
@Data
public class DocumentDetails {

	private boolean exists;
	private String name;
	private String type;
	private int size;
	private String userId;
	private List<DataModel> datas;
}
