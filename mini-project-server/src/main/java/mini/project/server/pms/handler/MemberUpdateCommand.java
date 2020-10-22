package mini.project.server.pms.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import mini.project.server.pms.domain.Member;
import mini.project.server.util.Prompt;

public class MemberUpdateCommand implements Command {

  List<Member> memberList;

  public MemberUpdateCommand(List<Member> list) {
    this.memberList = list;
  }

  @Override
  public void execute(PrintWriter out, BufferedReader in) {
    out.println("[회원 변경]");

    try {
      int no = Prompt.inputInt("번호? ", out, in);
      Member member = findByNo(no);

      if (member == null) {
        out.println("해당 번호의 회원이 없습니다.");
        return;
      }

      String name = Prompt.inputString(
          String.format("이름(%s)? ", member.getName()), out, in);
   
      String password = Prompt.inputString("암호? ");
     
      String response = Prompt.inputString("정말 변경하시겠습니까?(y/N) ", out, in);
      if (!response.equalsIgnoreCase("y")) {
        out.println("회원 변경을 취소하였습니다.");
        return;
      }

      member.setName(name);
      member.setPassword(password);
     
      out.println("회원을 변경하였습니다.");
    } catch (Exception e) {
      out.printf("작업 처리 중 오류 발생 !- %s\n", e.getMessage());
    }
  }

  private Member findByNo(int no) {
    for (int i = 0; i < memberList.size(); i++) {
      Member member = memberList.get(i);
      if (member.getNo() == no) {
        return member;
      }
    }
    return null;
  }
}