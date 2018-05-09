package freshman;

import java.util.Random;

public class TRandom {

	TVector fvector;
	private Random fRandom;
	private int fselect[];

	
	
	/**
	 * �R���X�g���N�^
	 */
	public TRandom(){
		fRandom = new Random();
		fvector = new TVector();
	}
	
	public TRandom(TRandom src) {
		fRandom = src.fRandom;
		fvector = new TVector(src.fvector);
	}

	
	/**
	 * ��̏�����
	 * @return
	 */
	public Random TSetSeed() {
		Random fRandom = new Random();
		return fRandom;
	}
	
	/**
	 * �����𔭐�
	 * @return
	 */
	public double TCreatRandom() {
		return fRandom.nextDouble();
	}
	
	/**
	 * num�̎��������𔭐�
	 * @param num
	 */
	public TRandom TCreatRandomDouble(int num) {
		if(fvector.getDimension() != num) {
			fvector.setDimension(num);
		}
		for(int i=0; i< num; ++i) {
			fvector.setElement(i, TCreatRandom());
		}
		return this;
	}
	
	/**
	 * num�̐��������𔭐�
	 * @param num
	 * @param pop
	 * @return
	 */
	public int[] TCreatRandomInt(int num, int pop) {
		fselect = new int[num];
		for(int i=0; i < num; ++i) {
			fselect[i] = fRandom.nextInt(pop);
			for(int j=0; j< i; ++j) {
				if(fselect[i] == fselect[j]) {
					--i;
					break;
				}
			}
		}
		return fselect;
	}
	
	/**
	 * ����E�����͈̔͂̎��������𔭐�
	 * @param min
	 * @param max
	 * @param num
	 * @return
	 */
	public TRandom TCreatRandomRange(double min, double max, int num) {
		// max < min�̎��͒l�����ւ��Ă݂�
		if(max < min) {
			double tmp = max;
			max = min;
			min = tmp;
		}
		double ave = (max + min)/2;
		double range = (max - min)/2;
		if(fvector.getDimension() != num) {
			fvector.setDimension(num);
		}
		for(int i=0; i< num; ++i) {
			fvector.setElement(i, ((TCreatRandom() -0.5)* range) + ave);
		}		
		return this;
	}
	
	public TRandom TCreatRandomSeiki(double m, double sigma, int num) {
		if(fvector.getDimension() != num) {
			fvector.setDimension(num);
		}
		for(int i=0; i < num; ++i) {
			fvector.setElement(i, (TCreatRandom() - 0.5) * sigma + m);
		}
		
		return this;
	}
	
	
	//���C���֐�
	public static void main(String[] args) {

		TRandomTest();
	}
	
	/**
	 * �e�X�g�֐�
	 */
		
	public static void TRandomTest() {
		TRandom ram1 = new TRandom();
		TRandom ram2 = new TRandom();
		int dimension = 5;

		ram1.TSetSeed();
		//������������
		ram1.TCreatRandomDouble(dimension);
		ram2.TCreatRandomDouble(dimension);
		System.out.println(ram1.fvector);
		System.out.println(ram2.fvector);
		
		//�ŏ��ƍő�̊Ԃ̗�������
		ram1.TCreatRandomRange(-5.0, 5.0, dimension);
		ram2.TCreatRandomRange(1.0, 5.0, dimension);
		System.out.println(ram1.fvector);
		System.out.println(ram2.fvector);
		
		ram1.TCreatRandomSeiki(0, 1.0, dimension);
		System.out.println(ram1.fvector);
		
		//������������
		int[] ramint = new int[dimension];
		ramint = ram1.TCreatRandomInt(dimension,10);
		for(int i=0; i< dimension; ++i) {
			System.out.print(ramint[i] + ", ");
		}
		System.out.println("");
		
	}
	
	
}
