package com.yc.jsdps.utils.video;

public enum TestPosId {

  /** 内容联盟测试PosId **/
  POSID_ENTRY_TYPE1(4000000536L), // 入口组件测试id 样式1
  POSID_ENTRY_TYPE2(4000000543L), // 入口组件测试id 样式 2；
  POSID_ENTRY_TYPE3(4000000550L), // 入口组件测试id 样式3
  POSID_ENTRY_TYPE4(4000000557L), // 入口组件测试id 样式 4；
  POSID_ENTRY_TYPE5(4000000564L), // 入口组件测试id 样式 5 Tab全场景类样式
  POSID_CONTENT_PAGE(4000000536L), // 内容联盟滑滑流测试PosId
  POSID_FEED_PAGE_1(4000000571L), // 内容联盟双Feed流测试PosId
  POSID_FEED_PAGE_2(4000000578L), // 内容联盟单Feed流小卡样式测试PosId
  POSID_FEED_PAGE_3(4000000585L), // 内容联盟双Feed流大卡样式测试PosId
  POSID_HOTSPOT_PAGE(4000000536L), // 热点列表组件测试id
  POSID_TUBE_PAGE(4000000536L), // 内容联盟短剧测试id
  /** 横版视频posId **/
  POSID_HORIZONTAL_FEED_PAGE(4000000663L), // 内容联盟横版视频PosId
  POSID_HORIZONTAL_IMAGE_PAGE(4000000659L), // 内容联盟横版视频+图文PosId

  /** 电商联盟测试PosId **/
  POSID_1(90009), // 直播入口id
  POSID_2(4000000290L); // 内容入口id



  public long posId;

  TestPosId(long posId) {
    this.posId = posId;
  }
}
