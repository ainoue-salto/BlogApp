/**
 * 
 */
function submitForm() {
	const title = document.getElementById('title').value; //タイトルの値
	const contentForm = document.getElementById('contentForm').value; //コンテンツの値
	console.log("コンソール1")
	console.log(title) //タイトルの値
	console.log("コンソール2")
	console.log(contentForm) //コンテンツの値
	//jsonの書き方{キー:値}
	//キーには""は要らない
	const data = {
		titleForm: title,
		contentForm: contentForm
	};

	fetch('/insert', {
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
				window.location.href = '/blog/form';
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('エラーが発生しました。');
			window.location.href = '/blog/form';
		});
}