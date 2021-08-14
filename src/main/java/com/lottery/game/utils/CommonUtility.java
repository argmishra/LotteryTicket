package com.lottery.game.utils;

import com.lottery.game.model.Number;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonUtility {

	public static Integer calculateResult(List<Integer> numbers ){
		Integer result = 0;
		if(numbers.get(0)+numbers.get(1)+numbers.get(2)==2){
			result = 10;
		}else if(numbers.get(0)==numbers.get(1) && numbers.get(0)==numbers.get(2) && numbers.get(1)==numbers.get(2)){
			result = 5;
		}else if(numbers.get(0)!=numbers.get(1) && numbers.get(0) != numbers.get(2)){
			result = 1;
		}
		return result;
	}

	public static Number generateLine() {
		Number number = new Number();
		List<Integer> numberList = new ArrayList();
		for(int i=0;i<3;i++) {
			numberList.add(new Random().nextInt(3));
		}

		number.setNumber(numberList);
		number.setResult(CommonUtility.calculateResult(numberList));
		return number;
	}


}
