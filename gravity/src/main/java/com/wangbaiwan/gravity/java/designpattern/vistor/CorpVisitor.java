package com.wangbaiwan.gravity.java.designpattern.vistor;

public class CorpVisitor implements Vistor
{
	double totolPrice;

	@Override

	public void visitCpu(Cpu cpu)
	{
		totolPrice += cpu.getPrice() * 0.8;
	}

	@Override
	public void visitMemory(Memory memory)
	{
		totolPrice += memory.getPrice() * 0.75;
	}
}
