package Scheduling;

import java.util.Scanner;

class SJF
{
	private String pname;
	private int burst;
	private int arrivaltime;
	
	SJF() {}
	private SJF(String nam, int bt, int at)
	{
		this.pname = nam;
		this.burst = bt;
		this.arrivaltime = at;
	}
	
	private static void calcwaittime(SJF p[], int n, int wait[], int context)
	{
		int[] rt = new int[n];
		for(int i = 0; i < n; i++)
		{
			rt[i] = p[i].burst;
			
		}
		int counter = 0, time = 0, minimum = Integer.MAX_VALUE;
		int shortest = 0, finishtime = 0, counting = 0;
		boolean check = false;
		while(counter != n)
		{
			for(int i = 0; i < n; i++)
			{
				if((p[i].arrivaltime <= time) && (rt[i] < minimum) && (rt[i] > 0))
				{
					minimum = rt[i];
					shortest = i;
					if(counting != 0)
						time += context;
					System.out.println("shortest " + p[i].pname + "\t" + time + "\t");
					check = true;
					counting++;
				}
				
			}
			if(check == false)
			{
				time++;
				continue;
			}
			rt[shortest]--;
			minimum = rt[shortest];
			if(minimum == 0)
			{
				minimum = Integer.MAX_VALUE;
			}
			if(rt[shortest] == 0)
			{
				counter++;
				check = false;
				finishtime = time + 1;
				System.out.println(finishtime + "\n");
				wait[shortest] = finishtime - p[shortest].burst - p[shortest].arrivaltime;
				if(wait[shortest] < 0) wait[shortest] = 0;
			}
			time++;
		}
	}
	
	private static void calcturnaroundtime(SJF p[], int n, int wait[], int turnaround[])
	{
		for(int i = 0; i < n; i++)
		{
			turnaround[i] = p[i].burst + wait[i];
		}
	}
	
	public static void sjfmain(SJF p[], int n, int con)
	{
		int wait[] = new int[n];
		int turnaround[] = new int[n];
		calcwaittime(p, n, wait, con);
		calcturnaroundtime(p, n, wait, turnaround);
		int avrgwait = 0;
		int avrgturn = 0;
		System.out.println("process" + " Burst " + " Wait " + " Turn Around ");
		for(int i = 0; i < n; i++)
		{
			avrgwait += wait[i];
			avrgturn += turnaround[i];
			System.out.println(" " + p[i].pname + "\t" + p[i].burst + "\t" + wait[i] + "\t" + turnaround[i]);
		}
		System.out.println("average wait: " + (float) avrgwait / (float) n);
		System.out.println("average turn around: " + (float) avrgturn / (float) n);
	}
	
	public void SJF_Main()
	{
		Scanner input = new Scanner(System.in);
		System.out.print("enter the number of processes : ");
		int noproc = input.nextInt();
		SJF proc[] = new SJF[noproc];
		int cont;
		System.out.print("Enter Context Switch time : ");
		cont = input.nextInt();
		for(int i = 0; i < noproc; i++)
		{
			System.out.println("enter the details of process " + (i + 1));
			System.out.print("process name : ");
			String name = input.next();
			System.out.print("process burst time : ");
			int bt = input.nextInt();
			System.out.print("process Arrival time : ");
			int at = input.nextInt();
			proc[i] = new SJF(name, bt, at);
		}
		SJF sjf = new SJF();
		sjf.sjfmain(proc, noproc, cont);
	}
}