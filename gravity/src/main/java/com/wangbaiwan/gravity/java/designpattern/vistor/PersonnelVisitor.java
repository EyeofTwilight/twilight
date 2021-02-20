package com.wangbaiwan.gravity.java.designpattern.vistor;

public class PersonnelVisitor implements Vistor
{
	double totolPrice;

	@Override

	public void visitCpu(Cpu cpu)
	{
		totolPrice += cpu.getPrice() * 0.9;
	}

	@Override
	public void visitMemory(Memory memory)
	{
		totolPrice += memory.getPrice() * 0.95;
	}
}
