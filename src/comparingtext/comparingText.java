package comparingtext;

import java.util.Random;

public class comparingText {
	//����ϵ��
	public static final double a1 = 5.3578547;
	public static final double a2 = 0.8356891;
	public static final double a3 = 37.293239;
	public static final double a4 = -40792.141;
	
	public static final double p1 = 85.334407;
	public static final double p2 = 0.0056858;
	public static final double p3 = 0.0006262;
	public static final double p4 = -0.0022053;
	
	public static final double q1 = 80.51249;
	public static final double q2 = 0.0071317;
	public static final double q3 = 0.0029955;
	public static final double q4 = 0.0021813;
	
	public static final double r1 = 9.300961;
	public static final double r2 = 0.0047026;
	public static final double r3 = 0.0012547;
	public static final double r4 = 0.0019085;
	
	//�㷨����
	private static int N = 500;  //��Ⱥ��
	private static int M = 10;  //ѡ�������
	private static int K = 4;  //��Ӣ������
	private static int L = 1;  //�ӽ�������
	
	private static double n[][] = new double[N][5];  //��ʼ��Ⱥ
	
	public void Run() {
		double m[][] = new double[M][5];  
		
		//������ɳ�ʼ��Ⱥ
		n = initial_p();
		
		//�ӽ���Ⱥ
		getMulti(m, 10);

		
		//��Ӣ�ุ���ӽ�
		eliteHybridization(m, n);
		

	/*for (int i=0; i<n.length; i++)
			System.out.println(f(n[i]) + "  " + H(n[i]));*/
	
	}
	
	//��Ӣ�ุ���ӽ�,
	public void eliteHybridization(double[][] a, double[][] b) {
		int i;
		int time = 1;
		boolean flag = true;
		double t[] = new double[5];
		
		sortByH(b);
		
		while (flag) {
			//sortByH(a);
			
			for (int l=0; l<K; l++)
				a[l] = b[l];
			
			for (i=K; i<a.length; i++) {
				a[i] = getSingle();
			}
			
			//printarray(a);
			
			t = singleHybridization(a);
			
			if (better(t, b[b.length-1])) {
				b[b.length-1] = t;
				sortByH(b);
			}


			time++;
			
			System.out.println("��" + time + "����" + "  �����壺" + f(b[b.length-1]) + "  ��ø��壺" + f(b[0]));
			

			if (Math.abs(f(b[b.length-1]) - f(b[0])) < Math.pow(10, -14)) {
				System.out.println("�㷨��������һ��������" + time + "���ӽ�");
				flag = false;
			}
			
			/*try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
				
		}
	}


	//�ӿռ��ӽ�����
	public double[] singleHybridization(double[][] a) {
		double t[] = new double[a.length];
		double k[] = new double[5];
		
		while (true) {
			for (int i=0; i<a.length; i++) {
				
				if (i == (a.length-1)) {
					t[i] = 1-arraySum(t);
					break;
				}
				
				t[i] = randDouble(-0.5, (1-arraySum(t)));
				
			}
		
			for (int j=0; j<5; j++)
				for (int i=0; i<a.length; i++)
					k[j] += t[i]*a[i][j];
			
	  	    if (k[0]<78) k[0] = 78;
			if (k[0]>102) k[0] = 102;
			if (k[1]<33) k[1] = 33;
			if (k[1]>45) k[1] = 45;
			if (k[2]<27) k[2] = 27;
			if (k[2]>45) k[2] = 45;
			if (k[3]<27) k[3] = 27;
			if (k[3]>45) k[3] = 45;
			if (k[4]<27) k[4] = 27;
			if (k[4]>45) k[4] = 45;

			
			//System.out.println(f(k) + "  " + H(k));
	
			
	
			if (restriction(k))
				break;
			
			for (int j=0; j<t.length; j++)
				t[j] = 0;
			for (int j=0; j<5; j++)
				k[j] = 0;
			break;
		}
		
		return k;
	}
	
	//��������Ԫ��֮��
	public double arraySum(double[] t) {
		double sum = 0;
		
		for (int i=0; i<t.length; i++)
			sum += t[i];

		return sum;
	}

	//��Ⱥ���򣬺õ���ǰ
	public void sortByH(double a[][]) {
		double t[] = new double[5];
		
		for (int i=0; i<a.length; i++)
			for (int j=i; j<a.length; j++) {
				if (better(a[j], a[i])) {
					t = a[i];
					a[i] = a[j];
					a[j] = t;				
				}
				
			}
	}
	
	//����Ƚ�,��ǰ�ߺ��򷵻�true
	public boolean better(double[] a, double[] b) {
		if (H(a) < H(b))
			return true;
		else if (H(a) == H(b)) {
			if (f(a) < f(b))
				return true;
		}
		
		return false;
	}
	
	//��Ӧֵ����
	public double H(double a[]) {
		double h1, h2, h3;
		
		//��Ӧ����Լ������
		if (p1+p2*a[1]*a[4]+p3*a[0]*a[3]+p4*a[2]*a[4]>=0 && p1+p2*a[1]*a[4]+p3*a[0]*a[3]+p4*a[2]*a[4]<=92)
			h1 = 0.0;
		else if (p1+p2*a[1]*a[4]+p3*a[0]*a[3]+p4*a[2]*a[4] < 0)
			h1 = -(p1+p2*a[1]*a[4]+p3*a[0]*a[3]+p4*a[2]*a[4]);
		else
			h1 = (p1+p2*a[1]*a[4]+p3*a[0]*a[3]+p4*a[2]*a[4]-92);
		
		
		if (q1+q2*a[1]*a[4]+q3*a[0]*a[1]+q4*a[2]*a[2]>=90 && q1+q2*a[1]*a[4]+q3*a[0]*a[1]+q4*a[2]*a[2]<=110)
			h2 = 0.0;
		else if (q1+q2*a[1]*a[4]+q3*a[0]*a[1]+q4*a[2]*a[2] <= 90)
			h2 = (90-(q1+q2*a[1]*a[4]+q3*a[0]*a[1]+q4*a[2]*a[2]));
		else 
			h2 = q1+q2*a[1]*a[4]+q3*a[0]*a[1]+q4*a[2]*a[2] - 110;
		
		
		if (r1+r2*a[2]*a[4]+r3*a[0]*a[2]+r4*a[2]*a[3]>=20 && r1+r2*a[2]*a[4]+r3*a[0]*a[2]+r4*a[2]*a[3]<=25)
			h3 = 0.0;
		else if (r1+r2*a[2]*a[4]+r3*a[0]*a[2]+r4*a[2]*a[3] <= 20)
			h3 = 20 - (r1+r2*a[2]*a[4]+r3*a[0]*a[2]+r4*a[2]*a[3]);
		else 
			h3 = r1+r2*a[2]*a[4]+r3*a[0]*a[2]+r4*a[2]*a[3] - 25;
		
		return h1+h2+h3;
		
	}
	public boolean restriction(double a[]) {
		if (p1+p2*a[1]*a[4]+p3*a[0]*a[3]+p4*a[2]*a[4]>=0 && p1+p2*a[1]*a[4]+p3*a[0]*a[3]+p4*a[2]*a[4]<=92
				&& q1+q2*a[1]*a[4]+q3*a[0]*a[1]+q4*a[2]*a[2]>=90 && q1+q2*a[1]*a[4]+q3*a[0]*a[1]+q4*a[2]*a[2]<=110
				&& r1+r2*a[2]*a[4]+r3*a[0]*a[2]+r4*a[2]*a[3]>=20 && r1+r2*a[2]*a[4]+r3*a[0]*a[2]+r4*a[2]*a[3]<=25
				&& a[0] >=78 && a[0] <=102 && a[1] >=33 && a[1] <=45 && a[2] >=27 && a[2] <= 45  && a[3] >=27 && a[3] <= 45
				&& a[4] >=27 && a[4] <= 45)
			return true;
		
		return false;
	}
	
	//����Ⱥ��ȡ��k���������ǰ��
	public void getMulti(double [][] a, int  k) {
		for (int i=0; i<k; i++)
			a[i] = getSingle();	
	}
	
	//����Ⱥ�����ȡ��һ������
	public double [] getSingle() {
		return n[new Random().nextInt(100)];
	}
	
	//�������
	public void printarray(double a[]) {
		for (int i=0; i<a.length; i++)
			System.out.print(a[i]+" ");
		System.out.println();
	}
	
	public void printarray(double a[][]) {
		for (int i=0; i<a.length; i++)
			printarray(a[i]);
	}
	
	//�����ʼ��Ⱥ
	public double[][] initial_p() {
		double n[][] = new double[N][5];
		for (int i=0; i<N; i++)
			for (int j=0; j<5; j++) {
				if (j == 0)
					n[i][j] = randDouble(78, 102);
				else if (j == 1)
					n[i][j] = randDouble(33, 45);
				else 
					n[i][j] = randDouble(27, 45);
			}
		
		return n;
	}

	/**
	 * Ŀ�꺯��
	 * @return�����غ���ֵ
	 */
	public double f(double array[]) {
		return a1*array[2]*array[2]+a2*array[0]*array[4]+a3*array[0]+a4;
	}

	/**
	 * �������
	 * @param min����ȷ��
	 * @param max����ȷ��
	 * @return ���������double��
	 */
	public double randDouble(double min, double max) {
		return min + ((max - min) * new Random().nextDouble());
	}
	
	
	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		new  comparingText().Run();
		long end = System.currentTimeMillis() - begin; 
		System.out.println("��ʱ��" + end + "����");
	}

}
