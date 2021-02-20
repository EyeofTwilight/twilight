package com.wangshen.eyeoftwilight.java.designpattern.vistor;

public class Computer
{
	ComputerPart cpu = new Cpu();
	ComputerPart memory = new Memory();

	public void accept(Vistor vistor)
	{
		this.cpu.accept(vistor);
		this.memory.accept(vistor);
	}
}
