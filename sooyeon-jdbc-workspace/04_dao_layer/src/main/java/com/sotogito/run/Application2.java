package com.sotogito.run;

import com.sotogito.model.dao.MenuDAO;
import com.sotogito.model.dto.CategoryDTO;
import com.sotogito.model.dto.MenuDTO;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static com.sotogito.common.JDBCTemplate.getConnection;

public class Application2 {
    public static void main(String[] args) {

        /**
         * ## 메뉴등록
         * 1. 마지막 메뉴 번호 조회
         * 2. 카테고리 전체 정보 조회
         * 3. 메뉴 등록
         */

        Connection con = getConnection();

        MenuDAO dao = new MenuDAO();

        List<CategoryDTO> categoryList = dao.selectCategoryList(con);
        categoryList.forEach(System.out::println);


        int lastMenuCode = dao.selectLastMenuCode(con);

        MenuDTO newMenuDTO = new MenuDTO();
        newMenuDTO.setMenuCode(lastMenuCode + 1);

        System.out.println("등록할 메뉴명,가격,카테고리코드,판매여부 입력");
        Scanner sc = new Scanner(System.in);
        newMenuDTO.setMenuName(sc.nextLine());
        newMenuDTO.setMenuPrice(Integer.parseInt(sc.nextLine()));
        newMenuDTO.setCategoryCode(Integer.parseInt(sc.nextLine()));
        newMenuDTO.setOrderableStatus(sc.nextLine().toUpperCase());

        boolean insertSuccess =  dao.insertMenu(con, newMenuDTO) == 1; //insert 되는 양이 1개이기 때문에 명확하게 1이라고 명시하는게 더 나은 거 같다

        if (insertSuccess) {
            System.out.println("성공");
            System.out.println(newMenuDTO);
        }else {
            System.out.println("실패");
        }

    }
}
