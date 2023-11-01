package ab2.impl.Kadiric;

import ab2.Ab2;

import ab2.AbstractHashMap;

import java.util.Arrays;
public class Ab2Impl implements Ab2 {

	@Override
	public AbstractHashMap newHashMapLinear(int minSize)
	{
		// TODO Auto-generated method stub
		return new HashMapLinear(minSize);
	}

	private void fillPrimes(int is_prime[], int n)
	{
		for(int i=0;i<=n;i++)
			is_prime[i]=1;
		for(int i=2;i<=n;i++)
		{
			for(int j=2;i*j<=n;j++)
				is_prime[i*j]=0;
		}
	}

	@Override
	public AbstractHashMap newHashMapQuadratic(int minSize)
	{
		// TODO Auto-generated method stub
		int is_prime[] = new int[minSize*4+1];
		fillPrimes(is_prime, minSize*4);
		while(is_prime[minSize]==0 || minSize%4!=3)
		{
			minSize++;
		}
		return new HashMapQuadratic(minSize);
	}

	@Override
	public AbstractHashMap newHashMapDouble(int minSize)
	{
		// TODO Auto-generated method stub
		int is_prime[] = new int[minSize*4+1];
		fillPrimes(is_prime, minSize*4);
		while(is_prime[minSize]==0)
		{
			minSize++;
		}
		int k=minSize-1;
		if(k==0)
			k=1;
		while(is_prime[k]==0)
		{
			k--;
		}
		return new HashMapDouble(minSize,k);
	}

	private void swap(int[] polje, int i, int j)
	{
		int temp = polje[i];
		polje[i] = polje[j];
		polje[j] = temp;
	}

	private int partition(int[] polje, int left, int right, int pIndex)
	{
		// pick `pIndex` as a pivot from the array
		int pivot = polje[pIndex];

		// Move pivot to end
		swap(polje, pIndex, right);

		// elements less than the pivot will be pushed to the left of `pIndex`;
		// elements more than the pivot will be pushed to the right of `pIndex`;
		// equal elements can go either way
		pIndex = left;

		// each time we find an element less than or equal to the pivot, `pIndex`
		// is incremented, and that element would be placed before the pivot.
		for (int i = left; i < right; i++)
		{
			if (polje[i] <= pivot)
			{
				swap(polje, i, pIndex);
				pIndex++;
			}
		}

		// move pivot to its final place
		swap(polje, pIndex, right);

		// return `pIndex` (index of the pivot element)
		return pIndex;
	}

	private int find_true_median(int polje[],int left, int right)
	{
		Arrays.sort(polje,left,right+1);
		return ((left+right)/2);
	}

	private int medmedselect(int polje[], int left, int right)
	{
		// 1. Divide the list into sublists of length five.
		// (Note that the last sublist may have length less than five.)
		// 2. find the true median of each sublist
		if(right-left+1<=5)
			return find_true_median(polje,left,right);
		int number_of_lists = (right-left+1)/5;
		int i=0;
		int l,r=-1,m,n;
		int[] novo_polje=new int[number_of_lists+1];
		for(i=0;i!=number_of_lists;i++)
		{

			l=left+i*5;
			r=l+4;
			m=find_true_median(polje,l,r);
			novo_polje[i]=polje[m];
		}
		// last sublist, smaller than 5
		if(r<right)
		{
			l=left+i*5;
			r=right;
			m = find_true_median(polje,l,r);
			novo_polje[i]=polje[m];
		}
		//3. Use the median of medians algorithm to recursively determine
		// the median of the set of all medians from the previous step.
		n=novo_polje.length;
		int med = medmedselect(novo_polje,0,n-1);
		// now  we need to find the element index in the source field
		for(i=0;i!=number_of_lists;i++)
		{
			l=left+i*5;
			r=l+4;
			m=(l+r)/2;
			if(polje[m]==novo_polje[med])
				return m;

		}
		l=left+i*5;
		r=right;
		m=(l+r)/2;
		return m;
	}

	private int quickselect(int polje[], int left, int right, int k)
	{
		// If the array contains only one element, return that element
		if (left == right)
		{
			return polje[left];
		}

		// select `pIndex` between left and right
		// Random abc = new Random(7);
		//int pIndex = left + abc.nextInt(right - left + 1);
		// int pIndex=right;
		int pIndex = medmedselect(polje,left,right);
		pIndex = partition(polje, left, right, pIndex);

		// The pivot is in its final sorted position
		if (k == pIndex)
		{
			return polje[k];
		}

		// if `k` is less than the pivot index
		else if (k < pIndex)
		{
			return quickselect(polje, left, pIndex - 1, k);
		}

		// if `k` is more than the pivot index
		else
		{
			return quickselect(polje, pIndex + 1, right, k);
		}
	}

	@Override
	public int quickselect(int[] data, int i)
	{
		// TODO Auto-generated method stub
		return quickselect(data,0,data.length-1,i-1);
	}
}