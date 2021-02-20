package com.wangbaiwan.gravity.java.designpattern.vistor;

public abstract class ComputerPart
{
	abstract void accept(Vistor vistor);

	abstract double getPrice();
}
