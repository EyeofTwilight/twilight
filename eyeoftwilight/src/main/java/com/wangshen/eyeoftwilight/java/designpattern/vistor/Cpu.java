package com.wangshen.eyeoftwilight.java.designpattern.vistor;

public class Cpu extends ComputerPart
{
	@Override
	void accept(Vistor vistor)
	{
		vistor.visitCpu(this);
	}

	@Override
	double getPrice()
	{
		return 500;
	}
}
