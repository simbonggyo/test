package com.example.TT.item.repository;
import java.time.LocalDateTime;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.TT.item.constant.itemSellStatus;
import com.example.TT.item.entity.Qitem;
import com.example.TT.item.entity.item;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;


@SpringBootTest
class ItemRepositoryTest {

	//new 안만들어도 객체 로딩
	@Autowired
	EntityManager em;
    
	@Autowired
    ItemRepository itemRepository;
    
    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        item item = new item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(itemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        
        item savedItem = itemRepository.save(item);
        
        System.out.println(savedItem.toString());
    }
    
    public void createItemList(){
        for(int i=1;i<=10;i++){
            item item = new item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(itemSellStatus.SELL);
            item.setStockNumber(100); 
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            
            item savedItem = itemRepository.save(item);
        }
    }
    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemList();
        List<item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for(item item : itemList){
            System.out.println(item.toString());
        }
    }
    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(item item : itemList){
            System.out.println(item.toString());
        }
    }
    
    @Test
    @DisplayName("JPQL 쿼리")
    public void findByitemDetailTest() {
    	createItemList();
    	
    	List<item> itemlist = itemRepository.findByitemDetail("테스트");
    	for (item item : itemlist) {
			System.out.println(item);
		}
    }
    
    @Test
    @DisplayName("Native 쿼리")
    public void findByitemDetailNativeTest() {
    	createItemList();
    	
    	List<item> itemlist = itemRepository.findByitemDetailNative("테스트");
    	for (item item : itemlist) {
			System.out.println(item);
		}
    }
    @Test
    @DisplayName("querydsl 테스트")
    public void querydslTest() {
    	createItemList();
    	
    	JPAQueryFactory QueryFactory = new JPAQueryFactory(em);
//    	Qitem qitem = new Qitem("i"); 
    	Qitem qitem = Qitem.item;
    	List<item> list = QueryFactory
    	.select(qitem)
    	.from(qitem)
    	.where(qitem.itemSellStatus.eq(itemSellStatus.SELL))
    	.where(qitem.itemDetail.like("%"+"1"+"%"))
    	.orderBy(qitem.price.asc())
    	.fetch(); //컨트롤 + 숫자 1번 -> 지역변수 선언
    	
//   JPAQuery<item> query =	QueryFactory
//    	.select(qitem)
//    	.from(qitem)
//    	.where(qitem.itemSellStatus.eq(itemSellStatus.SELL))
//    	.orderBy(qitem.price.asc());
//    	
//  List<item> list = query.fetch();
   
    	for (item item : list) {
			System.out.println(item);
		}
    }
    
    
    @Test
    void test() {
    	item item = new item();
    	System.out.println(item);
    }
}