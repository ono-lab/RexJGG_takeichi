package freshman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


//�x�N�g���̃N���X
public class TVector {

	private double [] fData;
	public static double EPS = 1e-10;

	//TVector()�̃R���X�g���N�^
	public TVector() {
		fData = new double[0];
	}
	
	public TVector(TVector src) {
		fData = new double[src.fData.length];
		for(int i=0; i < fData.length; ++i) {
			fData[i]= src.fData[i];
		}
	}
	
	//TVector�̃R�s�[
	public TVector CopyFrom(TVector src) {
		if(fData.length != src.fData.length) {
			fData = new double[src.fData.length];
		}
		for(int i= 0; i < fData.length; ++i) {
			fData[i]= src.fData[i];
		}
		return this;
	}
	
	@Override
	public TVector clone() {
		return new TVector(this);
	}
	
	//�����o��
	public void WriteTo(PrintWriter pw) {
		pw.println(fData.length);
		for(int i=0; i < fData.length; ++i) {
			pw.print(fData[i] + " " );
		}
		pw.println();
	}
	
	//�ǂݍ���
	public void ReadFrom(BufferedReader br) throws IOException{
		int dimension = Integer.parseInt(br.readLine());
		setDimension(dimension);
		String [] tokens = br.readLine().split(" ");
		for(int i= 0; i < dimension; ++i) {
			fData[i] = Double.parseDouble(tokens[i]);
		}
	}
	
	//������set
	public void setDimension(int dimension) {
		if(fData.length != dimension) {
			fData = new double[dimension];
		}
	}
	
	//������get
	public int getDimension() {
		return fData.length;
	}
	
	//�v�f��get
	public double getElement(int index) {
		return fData[index];
	}
	
	//�v�f��set
	public void setElement(int index, double e) {
		fData[index] = e;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i= 0; i < fData.length ; ++i) {
			str += fData[i] + " ";
		}
		return str;
	}
	
	//2��TVector��������
	@Override
	public boolean equals(Object o) {
		TVector v = (TVector)o; //�_�E���L���X�g
		assert fData.length == v.fData.length; //�A�T�[�g �R���p�C���� -e�Ŗ���
		for(int i=0; i < fData.length; ++i) {
			if(Math.abs(fData[i] - v.fData[i]) > EPS) {
				return false;
			}
		}
		return true;
	}
	
	//�v�f�̉��Z
	public TVector add(TVector v) {
		assert fData.length == v.fData.length;
		for(int i=0; i < fData.length; ++i) {
			fData[i] += v.fData[i];
		}
		return this;
	}
	
	//�v�f�̌��Z
	public TVector subtract(TVector v) {
		assert fData.length == v.fData.length;
		for(int i=0; i < fData.length; ++i) {
			fData[i] -= v.fData[i];
		}
		return this;
	}
	
	//�X�J���[�{����
	public TVector ScalorMul(double x) {
		for(int i=0; i < fData.length; ++i) {
			fData[i] *= x;
		}
		return this;
	}
	
	//�m�����̌v�Z
	public double calculateNorm(TVector v) {
		double norm = 0;
		for(int i=0; i < v.fData.length; ++i) {
			norm += Math.pow(v.fData[i],2); //2�悷��
		}
		norm = Math.sqrt(norm); //���[�g
		return norm;
	}
	
	//���K��
	public TVector normalize(TVector v) {
		double norm = 0;
		norm = calculateNorm(v);
		for(int i=0 ; i < v.fData.length; ++i) {
			v.fData[i] /= norm; 
		}
		return this;
	}
	
	
	//���C���֐�
	public static void main(String []args) {
		
		try {
			TVectorTest();
		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
		
	}
	
	//�e�X�g����֐�
	public static void TVectorTest() throws IOException{
		TVector v1 = new TVector();
		TVector v2 = new TVector();
		//������Ԃ�v1�m�F
		System.out.println("Test1");
		System.out.println(v1);
		System.out.println(v1.getDimension());
		
		v1.setDimension(3);
		v2.setDimension(3);
		
		//v1�ɒl����
		for(int i=0; i< v1.fData.length; ++i) {
			v1.fData[i] = i*5;
		}
		//v2�ɒl����
		for(int i=0; i< v1.fData.length; ++i) {
			v2.fData[i] = i + 3;
		}
			
		//������v1,v2���m�F
		System.out.println("\n" + "Test2");
		System.out.println("v1: "+ v1 + ", v2 :" + v2);
		System.out.println("v1_D: " + v1.getDimension() + ", v2_D: " + v2.getDimension());
		//���Z�E���Z�m�F �� v2���Z���v1����v2�����Z���Ă���̂�v1�ɖ߂�
		System.out.println("v1+v2: " + v1.add(v2) + ", v1-v2 :" + v1.subtract(v2));
		//���łɃX�J���[�{���m�F
		System.out.println("v1*5: " + v1.ScalorMul(5));
		//�m�����A���K���m�F
		System.out.println("v1_norm :" + v1.calculateNorm(v1) + ", v1_normalize :" + v1.normalize(v1));
		System.out.println("v2_norm :" + v2.calculateNorm(v2) + ", v2_normalize :" + v2.normalize(v2));

		System.out.println("\n" + "Test3");
		
		//v1,v2�ɒl����
		for(int i=0; i< v1.fData.length; ++i) {
			v1.fData[i] = i;
		}
		for(int i=0; i< v2.fData.length; ++i) {
			v2.fData[i] = i;
		}
		
		//v1,v2����v���Ă��邩�m�F
		System.out.println("v1=v2?: " + v1.equals(v2));
		//v1[0],v2[0]�ɒl����
		v1.setElement(0,0.333333333);
		v2.setElement(0, 1.0/3.0);
		System.out.println("v1=v2?: " + v1.equals(v2)); 
		System.out.println("v1: "+ v1 + ", v2 :" + v2);
		v1.setElement(0,0.333333333333);   //EPS�ȉ���v1[0]��ݒ肷��Έ�v
		System.out.println("v1=v2?: " + v1.equals(v2));
		System.out.println("v1: "+ v1 + ", v2 :" + v2);
		
		//�ǂݍ��݁E�����o��
			try {
				System.out.println("\n" + "Test4");
				v1.setElement(0,0);
				String str = v1.toString();
				System.out.println("v1:" + str);
					
				File filename = new File("test_TVector.txt"); 
				filename.createNewFile();
				

				//FileWriter�I�u�W�F�N�g�̐���
				FileWriter fw = new FileWriter(filename);

				//BufferedWriter�I�u�W�F�N�g�̐���
				BufferedWriter bw = new BufferedWriter(fw);

				//PrintWriter�I�u�W�F�N�g�̐���
				PrintWriter pw = new PrintWriter(bw);
				v1.WriteTo(pw);
				pw.close();
				
				//v2�̓��e��v1�ŏ�������
				BufferedReader br = new BufferedReader(new FileReader(filename)); 
				v2.ReadFrom(br); 
				br.close(); 
				System.out.println("v1: "+ v1 + ", v2 :" + v2);
				
			}catch(IOException e) { 
		 		System.out.println(e); 
			} 

	}
	
}
