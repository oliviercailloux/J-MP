package io.github.oliviercailloux.jmp.utils;

import io.github.oliviercailloux.jlp.mp.MPBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FakeDB {

	private Map<Integer, MPBuilder> problemList;

	public FakeDB() {
		problemList = new LinkedHashMap<>();
	}

	public Map<Integer, MPBuilder> getProblemList() {
		return problemList;
	}

	public void addPM(int id, MPBuilder mp) {
		problemList.put(id, mp);
	}

}