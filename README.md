# voodoo

voodoo是为解决热点数据更新问题而生。
update test.

## 基于事务的解决方案
Thread 1<br>
//start transaction<br>
resource.findById(id1)<br>
//do something cost time(300ms)...<br>
resource.update(item)<br>
//end transaction<br>

Thread 2<br>
//start transaction<br>
resource.findById(id1)<br>
//wait a few seconds(300ms) for select...<br>
//do something cost time(300ms)...<br>
resource.update(item)<br>
//end transaction<br>

在并发环境下，如果多个线程修改的是同一行数据，实际并发度为1，执行效率与串行执行一样。

## 基于voodoo的解决方案
Thread 1<br>
resource.findById(id1)<br>
/**********/<br>
1.create or load publisher from data bus.<br>
2.create subscriber<br>
3.subscribe publisher<br>
4.do something cost time(300ms)<br>
5.submit events to voodoo<br>
/**********/<br>
//start transaction<br>
resource.findById(id1)<br>
commit events<br>
resource.update(item)<br>
//end transaction<br>

Thread 2<br>
resource.findById(id1)<br>
/**********/<br>
1.create or load publisher from data bus.<br>
2.create subscriber<br>
3.subscribe publisher<br>
4.do something cost time(300ms)<br>
5.submit events to voodoo<br>
/**********/<br>
//start transaction<br>
resource.findById(id1)<br>
commit events<br>
resource.update(item)<br>
//end transaction<br>
在同传统方案相同的场景下，并发度可以达到(300s+5ms+5ms)/(5ms+5ms)=31，是传统方案的31倍。

## 测试执行结果
23:13:06.884 [main] INFO  c.h.v.h.HotspotDataBusPublisherFactory - init publisher success,busName:ADD_ITEM_EXT_BUS<br>
23:13:06.892 [main] INFO  c.h.v.h.DefaultHotspotDataBusSubscriber - add filter,filter:com.holliesyin.voodoo.hotspot.EventTypeFilter<br>
23:13:06.892 [main] INFO  c.h.v.h.DefaultHotspotDataBusSubscriber - add filter,filter:com.holliesyin.voodoo.hotspot.IdFilter<br>
23:13:06.894 [main] INFO  c.h.v.h.DefaultHotspotDataBusSubscriber - add handler,handler:com.holliesyin.voodoo.hotspot.AddItemExtPropertyHandler<br>
23:13:06.894 [main] INFO  c.h.v.h.ItemExtHotspotDataBusSubscriber - create subscriber success.id:0<br>
23:13:06.894 [main] INFO  c.h.v.h.DefaultHotspotDataBusSubscriber - subscribe publisher.<br>
23:13:06.994 [main] INFO  c.h.v.h.DefaultHotspotDataBusPublisher - publish event,event:AddItemExtPropertyEvent{id='0', key='key1', value='value1'},clients count:1<br>
23:13:07.034 [main] INFO  c.h.v.h.DefaultHotspotDataBusSubscriber - accept event,event:AddItemExtPropertyEvent{id='0', key='key1', value='value1'}<br>
23:13:07.134 [main] INFO  c.h.v.h.DefaultHotspotDataBusPublisher - publish event,event:AddItemExtPropertyEvent{id='0', key='key2', value='value2'},clients count:1<br>
23:13:07.134 [main] INFO  c.h.v.h.DefaultHotspotDataBusSubscriber - accept event,event:AddItemExtPropertyEvent{id='0', key='key2', value='value2'}<br>
23:13:07.234 [main] INFO  c.h.v.h.DefaultHotspotDataBusPublisher - publish event,event:AddItemExtPropertyEvent{id='0', key='key1', value='value3'},clients count:1<br>
23:13:07.234 [main] INFO  c.h.v.h.DefaultHotspotDataBusSubscriber - accept event,event:AddItemExtPropertyEvent{id='0', key='key1', value='value3'}<br>
23:13:07.234 [main] INFO  c.h.v.h.DefaultHotspotDataBusSubscriber - commit subscriber.<br>
23:13:07.235 [main] INFO  c.h.voodoo.hotspot.ItemResource - find item in db,id:0<br>
23:13:07.236 [main] INFO  c.h.v.h.AddItemExtPropertyHandler - try to add ext property,event:AddItemExtPropertyEvent{id='0', key='key1', value='value1'},item:Item{id='0', ext=''}<br>
23:13:07.288 [main] INFO  c.h.v.h.AddItemExtPropertyHandler - try to add ext property,event:AddItemExtPropertyEvent{id='0', key='key2', value='value2'},item:Item{id='0', ext='{"key1":"value1"}'}<br>
23:13:07.292 [main] INFO  c.h.v.h.AddItemExtPropertyHandler - try to add ext property,event:AddItemExtPropertyEvent{id='0', key='key1', value='value3'},item:Item{id='0', ext='{"key1":"value1","key2":"value2"}'}<br>
23:13:07.293 [main] INFO  c.h.voodoo.hotspot.ItemResource - update item ext with occLock success,item:Item{id='0', ext='{"key1":"value3","key2":"value2"}'},occLock:<br>
23:13:07.293 [main] INFO  c.h.v.h.AddItemExtPropertyHandler - add new ext property with lock success,item:Item{id='0', ext='{"key1":"value3","key2":"value2"}'}<br>
