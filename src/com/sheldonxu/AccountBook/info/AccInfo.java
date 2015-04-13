package com.sheldonxu.AccountBook.info;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sheldon.Xu on 15-4-10.
 */
public class AccInfo {
    /*
    * <a>
	<b b_id = "01">
		<bn>收入</bn>
		<c c_id="0101">
			<cn>工资</cn>
		</c>
		<c c_id="0102">
			<cn>外快</cn>
		</c>
	</b>

	<b b_id = "02">
		<bn>支出</bn>
		<c c_id="0201"><cn>吃</cn>
			<d d_id="020101">日常</d>
			<d d_id="020102">请客</d>
			<d d_id="020103">烟酒</d>
		</c>
		<c c_id="0202"><cn>穿</cn>
			<d d_id="020201">自用</d>
			<d d_id="020202">礼物</d>
		</c>
		<c c_id="0203"><cn>住</cn>
			<d d_id="020301">房租</d>
			<d d_id="020302">水电费</d>
		</c>
		<c c_id="0204"><cn>行</cn>
			<d d_id="020401">公交车</d>
			<d d_id="020402">出租</d>
		</c>
		<c c_id="0205"><cn>用</cn>
			<d d_id="020501">学习用品</d>
			<d d_id="020502">生活用品</d>
		</c>
	</b>
</a>
    * */
    public static Map<String, String> getAccInfo(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("0101","工资");
        map.put("0102","外快");
        map.put("020101","日常");
        map.put("020102","请客");
        map.put("020103","烟酒");
        map.put("020201","自用");
        map.put("020202","礼物");
        map.put("020301","房租");
        map.put("020301","水电费");
        map.put("020401","公交车");
        map.put("020402","出租");
        map.put("020501","学习用品");
        map.put("020502","生活用品");
        return map;
    }

}
