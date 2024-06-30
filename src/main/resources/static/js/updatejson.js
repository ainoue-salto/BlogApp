/**
 * 
 */
function submitForm() {
	const title = document.getElementById('title').value; //タイトルの値
	const contentForm = document.getElementById('contentForm').value; //コンテンツの値
	const id = document.getElementById('id').value; //選択した投稿ID
	console.log("タイトル：")
	console.log(title) //タイトルの値
	console.log("投稿内容：")
	console.log(contentForm) //コンテンツの値
	console.log("投稿ID：")
	console.log(id) //コンテンツの値
	//jsonの書き方{キー:値}
	//キーには""は要らない
	const data = {
		titleForm: title,
		contentForm: contentForm,
		id: id
	};

	fetch('/update', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	})
		.then(response => {
			if (response.ok) {
				window.location.href = '/blog';
			} else {
				window.location.href = '/blog/updateform';
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('エラーが発生しました。');
			window.location.href = '/blog/updateform';
		});
}