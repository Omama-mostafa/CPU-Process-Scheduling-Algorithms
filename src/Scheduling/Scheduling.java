package Scheduling;

import java.util.Scanner;

public class Scheduling
{
	public static void main(String [] args)
	{
		System.out.println("To Use SJF press 1");
		System.out.println("To Use Round Robin press 2");
		System.out.println("To Use Priority press 3");
		System.out.println("To Use AG press 4");
		Scanner read = new Scanner(System.in);
		int num;
		num = read.nextInt();
		if(num == 1)
		{
			SJF s = new SJF();
			s.SJF_Main();
		}
		if(num == 2)
		{
			Round_Robin r = new Round_Robin();
			r.read_user();
		}
		if(num == 3)
		{
			Priority p = new Priority();
			p.Priority_Main();
		}
		if(num == 4)
		{
			AG a = new AG();
			a.AG_Main();
		}
	}
}
