package Interview;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HitachiInterview {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter number of elements in array");
		int n=sc.nextInt();
		int[] a=new int[n];
		//[2, 1, 3, 5, 3, 2]  --3
		// [3,4,4,3]   --4
		//[2, 2], --2
		//[2, 4, 3, 5, 1]  --> -1
		for(int i=0;i<n;i++)
		{
			System.out.println("Enter number ");
			a[i]=sc.nextInt();
		}
		System.out.println("Number with 2nd occurence has smaller index is : "+secondOccurrence(n,a));
     sc.close();
	}

	private static int secondOccurrence(int n, int[] a) {
		Set<Integer> set=new HashSet<>();
		for(int i:a)
		{
			if(set.contains(i))
				return i;
			else
				set.add(i);	
		}
		return -1;
	}

}
