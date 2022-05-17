# 文档说明

## 数据表结构

讲解视频实在太大，不可能全塞进apk里面的，所以可以上传到bilibili，然后在webview里播放

## 1.Topic(用于查询每个科目对应的学期周数专题以及视频链接和专题类型)，示例：(就算是exercise类型，url也不能为空，随便写点就行)

| subject | term       | week        | topic       | topic_type | video_url |
| ------- | ---------- | ----------- | ----------- | ---------- | --------- |
| chinese | 六年级上册 | 第一周 xxxx | 专题一 xxxx | video      | xxxxx     |
| chinese | 六年级上册 | 第一周 xxxx | 专题一 xxxx | exercise   | Xxxxx     |

### 2.Question(用于查询专题练习对应的题目答案分值讲解视频和相似题目)

| subject | term       | Week        | topic      | title                                                        | answer | score | video_url | similarities_id |
| ------- | ---------- | ----------- | ---------- | ------------------------------------------------------------ | ------ | ----- | --------- | --------------- |
| chinese | 六年级上册 | 第一周 xxxx | 专题一 xxx | 已知两个数的和与其中的一个加数，求另一个加数的运算，叫作（　　）法。 | 减     | 2     | xxx       | 1               |



### 3.Errorbook(用于储存错题)

| title                                                        | answer | video_url |
| ------------------------------------------------------------ | ------ | --------- |
| 已知两个数的和与其中的一个加数，求另一个加数的运算，叫作（　　）法。 | 减     | xxx       |

