package Scheduling;

import com.sun.corba.se.internal.Interceptors.PIORB;

import java.util.ArrayList;
import java.util.Scanner;

class Priority
{
	private int priority;
	private int burst_time;
	private String name;
	private int arrival_time;
	private int waiting_time;
	private static ArrayList<Integer> wait = new ArrayList<>();
	private static ArrayList<Integer> turnaround = new ArrayList<>();
	private static ArrayList<String> A_pro_name = new ArrayList<>();
	private static ArrayList<String> pro_seq = new ArrayList<>();
	private static ArrayList<Integer> time_seq = new ArrayList<>();
	
	Priority()
	{
		burst_time = 0;
		priority = 0;
		arrival_time = 0;
		name = "";
		waiting_time = 0;
	}
	
	private Priority(String w, int r, int x, int y)
	{
		name = w;
		arrival_time = r;
		burst_time = x;
		priority = y;
	}
	
	private static void Print(ArrayList<Priority> q)
	{
		System.out.println("Name\t" + "ArrivalTime\t" + "BurstTime\t" + "Priority\t" + "Wait");
		for(int i = 0; i < q.size(); i++)
			System.out.println(q.get(i).name + "\t\t\t" + q.get(i).arrival_time + "\t\t\t" + q.get(i).burst_time + "\t\t\t" + q.get(i).priority + "\t\t" + q.get(i).waiting_time);
	}
	
	
	private static void Get_input(int pro_num, ArrayList<Priority> queue)
	{
		Scanner input = new Scanner(System.in);
		String name;
		int arrivaltime;
		int bursttime;
		int priority;
		
		for(int i = 0; i < pro_num; i++)
		{
			System.out.print("\nProcess Name : ");
			name = input.next();
			System.out.print("Process Arrival Time : ");
			arrivaltime = input.nextInt();
			System.out.print("Process Burst Time : ");
			bursttime = input.nextInt();
			System.out.print("Process Priority : ");
			priority = input.nextInt();
			Priority obj = new Priority(name, arrivaltime, bursttime, priority);
			queue.add(obj);
		}
	}
	
	
	private static void Arrival_Sort(ArrayList<Priority> queue)
	{
		for(int i = 0; i < queue.size(); i++)
		{
			for(int j = i; j < queue.size(); j++)
			{
				if(queue.get(i).arrival_time > queue.get(j).arrival_time)
				{
					Priority Temp = queue.get(i);
					queue.set(i, queue.get(j));
					queue.set(j, Temp);
				}
			}
		}
	}
	
	private static void Priority_Sort(ArrayList<Priority> queue)
	{
		for(int i = 0; i < queue.size(); i++)
		{
			for(int j = i; j < queue.size(); j++)
			{
				if(queue.get(i).priority > queue.get(j).priority)
				{
					Priority Temp = queue.get(i);
					queue.set(i, queue.get(j));
					queue.set(j, Temp);
				}
			}
		}
	}
	
	private static void Solve_Starvation(int Before, int After, ArrayList<Priority> queue)
	{
		if(Before != After)
		{
			for(int i = 0; i < queue.size(); i++)
			{
				if(queue.get(After).arrival_time > queue.get(i).arrival_time && queue.get(i).priority >= 1)
					queue.get(i).priority--;
			}
		}
	}
	
	private static void Process_queue(ArrayList<Priority> queue)
	{
		int total_BT = 0 , count = 0;
		
		ArrayList<Boolean> flag = new ArrayList<>();
		ArrayList<String> B_pro_name = new ArrayList<>();
		ArrayList<Integer> burst = new ArrayList<>();
		
		for(int i = 0; i < queue.size(); i++)
		{
			total_BT += queue.get(i).burst_time;
			flag.add(false);
			B_pro_name.add(queue.get(i).name);
			burst.add(queue.get(i).burst_time);
		}
		
		while(count < total_BT)
		{
			ArrayList<Priority> FirstP = new ArrayList<>();
			for(int i = 0; i < queue.size(); i++)
			{
				if(queue.get(i).arrival_time <= count)
					FirstP.add(queue.get(i));
				if(FirstP.size() == queue.size())
					break;
			}
			
			Priority_Sort(FirstP);
			Print(FirstP);
			FirstP.get(0).burst_time--;
			pro_seq.add(FirstP.get(0).name);
			time_seq.add(count);
			
			int Bindex = flag.indexOf(true);
			for(int i = 0; i < queue.size(); i++)
			{
				if(queue.get(i).name.equals(FirstP.get(0).name))
				{
					flag.set(i, true);
				}
				else
				{
					flag.set(i, false);
				}
			}
			int Aindex = flag.indexOf(true);
			
			Solve_Starvation(Bindex, Aindex, queue);
			for(int i = 0; i < queue.size(); i++)
			{
				if(i != Aindex && queue.get(i).arrival_time <= count)
					queue.get(i).waiting_time++;
			}
			if(FirstP.get(0).burst_time == 0)
			{
				int index = queue.indexOf(FirstP.get(0));
				wait.add(queue.get(index).waiting_time);
				A_pro_name.add(queue.get(index).name);
				queue.remove(index);
				flag.remove(index);
			}
			count++;
		}
		int n = 0;
		while(n != wait.size())
		{
			turnaround.add(wait.get(n) + burst.get(B_pro_name.indexOf(A_pro_name.get(n))));
			n++;
		}
	}
	
	private static void Process_info()
	{
		float ave_wait = 0;
		float ave_turn = 0;
		for(int i = 0; i < wait.size(); i++)
		{
			ave_wait += wait.get(i);
			ave_turn += turnaround.get(i);
		}
		ave_wait = (float) ave_wait / wait.size();
		ave_turn = (float) ave_turn / turnaround.size();
		System.out.println("\nProcess Information : ");
		System.out.println("Process Name \t" + "Waiting Time \t" + "Turnaround Time");
		for(int i = 0; i < wait.size(); i++)
			System.out.println(A_pro_name.get(i) + "\t\t\t\t\t" + wait.get(i) + "\t\t\t\t\t" + turnaround.get(i));
		
		System.out.println("Average Wait Time = " + ave_wait);
		System.out.println("Average Turnaround Time = " + ave_turn);
		
		System.out.println("\nProcess Execution Order : ");
		//System.out.println(pro_seq);
		//System.out.println(time_seq);
		
		String s = pro_seq.get(0);
		System.out.println(pro_seq.get(0) + "\t\t" + time_seq.get(0));
		for(int i =1; i < time_seq.size(); i++)
		{
			if(!pro_seq.get(i).equals(s))
			{
				System.out.println(pro_seq.get(i) + "\t\t" + time_seq.get(i));
				s = pro_seq.get(i);
			}
		}
		System.out.println(pro_seq.get(pro_seq.size()-1) + "\t\t" + time_seq.get(pro_seq.size()-1));
	}
	
	public void Priority_Main()
	{
		int num_of_processes = 0;
		Scanner input = new Scanner(System.in);
		
		ArrayList<Priority> queue = new ArrayList<Priority>();
		System.out.print("Please enter the number of process : ");
		num_of_processes = input.nextInt();
		
		Get_input(num_of_processes, queue);
		Arrival_Sort(queue);
		Process_queue(queue);
		Process_info();
	}
}
