package com.wangshen.eyeoftwilight.java.designpattern.vistor;

public interface Vistor
{
	void visitCpu(Cpu cpu);

	void visitMemory(Memory memory);
}
