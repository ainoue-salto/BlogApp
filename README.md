## アプリ名
BlogApp

## アプリの概要
APIを用いてWeb上からDBに対してcreate,insert,update,deleteを行う簡易的な顧客情報管理アプリ

## アーキテクチャ
Java17/Springframework.boot3.3.0/Apache Tomcat10/PostgreSQL16.3

## Curlコマンド
登録処理と編集処理APIはCurlコマンドからでも操作確認済
以下のコマンドで実施可能
■登録処理
curl -X POST http://localhost:8080/insert -H "Content-Type: application/json" -d "{\"titleForm\": \"Your
Title\", \"contentForm\": \"Your content goes here.\"}"

■編集処理　※idに対応する数字は編集したい投稿データの主キーを参照
curl -X POST http://localhost:8080/update -H "Content-Type: application/json" -d "{\"id\": 35, \"titleFor
m\": \"Updated Title\", \"contentForm\": \"Updated content goes here.\"}"
{"message": "更新成功", "redirect": "/blog"}
