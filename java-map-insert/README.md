# 概要

データをマスキングする際にマスキングデータをデータベースへ保存して、エラーが発生した時にデータベースからマスキングデータを再ロードして使えるようにするためのテストコードである

# テーブル作成

```
CREATE TABLE MATCH_DATA(
    "KEY_NAME" VARCHAR2(255 BYTE), 
    "SEQ_NAME" VARCHAR2(255 BYTE),
    "DATA_BLOB" BLOB, 
    PRIMARY KEY ("KEY_NAME")
);
```

# 環境変数

```
URL=jdbc:oracle:thin:@//localhost:1521/{SID};USER={USER};PASSWORD={PASSWORD}
```