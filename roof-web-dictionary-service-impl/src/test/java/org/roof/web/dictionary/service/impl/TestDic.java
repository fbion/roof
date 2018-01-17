package org.roof.web.dictionary.service.impl;

import java.util.List;

import javax.persistence.Table;

import org.roof.web.dictionary.entity.Dictionary;

@Table(name = "t_TestDic")
public class TestDic {
	private Long id;

	private String name;

	private Dictionary time_period;

	private List<Dictionary> record_type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dictionary getTime_period() {
		return time_period;
	}

	public void setTime_period(Dictionary time_period) {
		this.time_period = time_period;
	}

	public List<Dictionary> getRecord_type() {
		return record_type;
	}

	public void setRecord_type(List<Dictionary> record_type) {
		this.record_type = record_type;
	}

}
