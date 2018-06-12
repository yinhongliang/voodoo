# voodoo

voodoo是为解决热点数据更新问题而生。

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
