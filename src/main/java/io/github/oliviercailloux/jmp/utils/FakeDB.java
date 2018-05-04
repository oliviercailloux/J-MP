package io.github.oliviercailloux.jmp.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.github.oliviercailloux.jlp.mp.MPBuilder;

@ApplicationScoped
public class FakeDB {

	private Map<Integer, MPBuilder> problemList;

	public FakeDB() {
		problemList = new LinkedHashMap<>();
	}

	public void addPM(int id, MPBuilder mp) {
		problemList.put(id, mp);
	}

	public Map<Integer, MPBuilder> getProblemList() {
		return problemList;
	}

}