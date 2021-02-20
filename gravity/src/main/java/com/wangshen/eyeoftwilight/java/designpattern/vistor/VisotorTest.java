package com.wangshen.eyeoftwilight.java.designpattern.vistor;

public class VisotorTest
{
	public static void main(String[] args)
	{
		PersonnelVisitor p = new PersonnelVisitor();
		new Computer().accept(p);
		System.out.println(p.totolPrice);

		CorpVisitor c = new CorpVisitor();
		new Computer().accept(c);
		System.out.println(c.totolPrice);
	}
}
