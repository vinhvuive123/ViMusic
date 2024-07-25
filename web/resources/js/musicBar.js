/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
/* global songIdex, songList */

const audio = document.getElementById('audio');
const playPauseButton = document.getElementById('play-pause-button');
const progressBar = document.getElementById('progress');
const volumeControl = document.getElementById('volume-control');

const repeatButton = document.getElementById("repeat-button");
const playerCurrentTime = document.querySelector(".player-time-current");
const playerDuration = document.querySelector(".player-time-duration");

//const songList = ["/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/Brianstorm.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/D Is For Dangerous.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/Do Me A Favour.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/Fluorescent Adolescent.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/If You Were There, Beware.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/Old Yellow Bricks.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/Only Ones Who Know.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/Teddy Picker.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/The Bad Thing.mp3",
//	"/PRJ301_SE1810_SP24_2/resources/songs/My Favourite Worst Nightmare (Vinyl)/This House Is A Circus.mp3"
//];

let songIndex = 0;
let artist = "";
let title = "";
let src = "";


function loveAction(sid) {
    let loveBtn = document.getElementById("love" + sid);
    if (loveBtn.style.color === "rgb(255, 22, 69)") {
        loveBtn.style.color = "white";
        // Gọi hàm updateLove với tham số là 2 (Xóa yêu thích) và sid
        updateLove(2, sid);
        // Hiển thị thông báo khi xóa yêu thích
        alert("Bạn đã xóa bài hát khỏi danh sách yêu thích.");
    } else {
        loveBtn.style.color = "rgb(255, 22, 69)";
        // Gọi hàm updateLove với tham số là 1 (Thêm yêu thích) và sid
        updateLove(1, sid);
        // Hiển thị thông báo khi thêm yêu thích
        alert("Bạn đã thêm bài hát vào danh sách yêu thích.");
    }
}


function updateLove(type, sid) {
	console.log(type);
//	xhr.open('GET', '/PRJ301_SE1810_SP24_2/love1?type=' + type + '&sid=' + sid, true);
	$.ajax({
		url: "/PRJ301_SE1810_SP24_2/love1",
		type: "get", //send it through get method
		data: {
			type: type,
			sid: sid
		},
		success: function (data) {
			console.log(data);
		},
		error: function (xhr) {
			//Do Something to handle error
		}
	});
}


function jumpAt(index) {
	console.log(index);
	songIndex = index;
	audio.src = songList[songIndex];
	loadDataMp3();
	audio.load();
	togglePlayPause();

}
function loadDataMp3() {
	src = songList[songIndex];
	var xhr = new XMLHttpRequest();
	xhr.open('GET', '/PRJ301_SE1810_SP24_2/MusicBarController?src=' + src, true);
	xhr.onreadystatechange = function () {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var response = JSON.parse(xhr.responseText);
			artist = response.artist;
			title = response.title;
			console.log(artist);
			console.log(title);
			document.getElementById("titleSong").textContent = title;
			document.getElementById("artist").textContent = artist;
			document.title = title + " - " + artist;

		}
	};
	xhr.send();
}


function changeVolume() {
	audio.volume = volumeControl.value;
}

audio.addEventListener('timeupdate', updateProgressBar);

// update progress bar and time 00:00
function updateProgressBar() {
	const currentTime = audio.currentTime;
	const duration = audio.duration;
	const progress = (currentTime / duration) * 100;
	progressBar.style.width = progress + '%';
	playerCurrentTime.textContent = formatTime(currentTime);
	playerDuration.textContent = formatTime(duration);
}

function formatTime(time) {
	const minutes = Math.floor(time / 60);
	const seconds = Math.floor(time % 60);
	return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
}

// update progress bar when click 
function updateProgressOnClick(event) {
	const progressBarRect = document.getElementById('progress-bar').getBoundingClientRect();
	const clickX = event.clientX - progressBarRect.left;
	const progress = (clickX / progressBarRect.width) * 100;
	document.getElementById('progress').style.width = progress + '%';
	const newTime = (progress / 100) * audio.duration;
	audio.currentTime = newTime;
}

// playPause button
function togglePlayPause() {
	if (audio.paused) {
		audio.play();
		playPauseButton.innerHTML = "<i class='uil uil-pause media-btn'></i>";
	} else {
		audio.pause();
		playPauseButton.innerHTML = "<i class='uil uil-play media-btn'></i>";
	}
}


// previouns button 
function previousBtn() {
	if (audio.currentTime < 3) {
		songIndex -= 1;
		if (songIndex >= 0) {
			audio.src = songList[songIndex];
			loadDataMp3();
			audio.load();
			togglePlayPause();
		} else if (songIndex < 0) {

		}


	} else {
		goBack();
	}
}

// forward button 
function forwardBtn() {
	songIndex += 1;
	if (songIndex < songList.length) {
		updateSongIndex();

	} else if (songIndex >= songList.length - 1) {
		songIndex = 0;
		updateSongIndex();

	}
}
function updateSongIndex() {
	audio.src = songList[songIndex];
	loadDataMp3();
	audio.load();
	togglePlayPause();
}
audio.addEventListener('loadedmetadata', function () {
	const title = audio.title;
	const artist = audio.artist;

});
// play back song
function goBack() {
	document.getElementById('progress').style.width = '0%';
	audio.currentTime = 0;
	audio.play();
	playPauseButton.innerHTML = "<i class='uil uil-pause media-btn'></i>";
}

let repeatStatus = false;
function repeatFn() {
	repeatStatus = !repeatStatus;
	console.log(repeatStatus);

	if (repeatStatus) {
		repeatButton.style.color = "green";
	} else {
//		audio.removeEventListener("ended", goBack);
		repeatButton.style.color = "black";
	}
}
audio.addEventListener("ended", () => {
	if (!repeatStatus) {
		if (songIndex == songList.length - 1) {
			songIndex = 0;
			loadDataMp3();
			audio.load();
			togglePlayPause();
		} else {

			forwardBtn();
		}
	} else {
		goBack();
	}
});

// first time load music bar
window.addEventListener("load", () => {
	const audio = document.getElementById('audio');
	audio.src = songList[songIndex];
	const currentTime = audio.currentTime;
	const duration = audio.duration;
	playerCurrentTime.textContent = formatTime(currentTime);
	playerDuration.textContent = formatTime(duration);
	loadDataMp3();
}, true);


