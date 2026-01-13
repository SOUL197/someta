package kr.co.ictedu.someta.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCountCheckVO {
	private boolean success; // 이메일 전송 요청. → true 성공 / false 실패
	private String reason; // 실패 시 실패한 이유 / 성공 시 상태 메시지
}
