// 개인정보 수정 =========================================================

const errorNickname = document.getElementById('errorNickname');
const errorAuth = document.getElementById('errorAuth');

// 프로필 사진 수정
function checkFileSize(inputElement) {
    const file = inputElement.files[0];

    // 파일 크기를 확인하고 1MB를 초과하는 경우
    if (file && file.size > 1048576) {
        showGlobalDialogue("파일 크기가 1MB를 초과합니다.<br>더 작은 파일을 선택해주세요.")
        inputElement.value = '';
        return;
    }

    performUpload(file);
}

function performUpload(file) {
    const profileImgForm = document.querySelector("form[name='file-form']");

    const formData = new FormData();
    formData.append("uploadFile", file)

    // /file/upload 엔드포인트에 POST 요청
    fetch('/file/upload', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('파일 업로드 실패');
            }
            return response.json();
        })
        .then(uuid => {
            let input = document.createElement("input");
            input.name = "uuid";
            input.value = uuid[0];
            input.type = "hidden";
            profileImgForm.append(input);

            profileImgForm.submit();
        })
        .catch(error => {
            console.error('파일 업로드 에러:', error);
            showGlobalDialogue("오류가 발생했습니다.<br>다시 시도해 주세요.")
        });
}

function updateProfileImage(uuid, fileName) {
    let now = new Date();
    let year = now.getFullYear();
    let month = now.getMonth() + 1;
    let date = now.getDate();
    month = month < 10 ? '0' + month : month;
    date = date < 10 ? '0' + date : date;

    let uploadPath = `${year}/${month}/${date}`;

    // JSON 데이터를 body에 담아 POST 요청 보내기
    fetch('/mypage/updatePersonalImg', {
        method: 'POST',
        headers: {
            'Content-Type': '/json'
        },
        body: JSON.stringify({
            fileName: fileName,
            uuid: uuid,
            uploadPath: uploadPath
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('파일 업데이트 실패');
            }
            return response.json();
        })
        .catch(error => {
            console.error('파일 업데이트 에러:', error);
            showGlobalDialogue("업데이트중 오류가 발생했습니다.<br>다시 시도해 주세요.")
        });
}


// 디폴트 사진 선택
function updateDefaultImg(imgName) {
    document.getElementById('selectedImageNumber').value = imgName;
    document.getElementById('imageForm').submit();
}

// 닉네임 수정
const nicknameChangeButton = document.getElementById('nicknameChangeButton');
const nicknameInput = document.getElementById('newNickname');

function openNicknameModal(nicknameModal) {
    nicknameInput.value = "";
    errorNickname.innerHTML = '';
    nicknameChangeButton.setAttribute("disabled", "disabled");
    openModal(nicknameModal);
}

function changeNickname(nickname) {

    fetch("/mypage/updateNickName?nickname=" + nickname, {
        method: "PATCH",
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw response;
            }
        })
        .then(async message => {
            closeModal();
            document.getElementById('nickname').innerHTML = nickname;
            document.getElementById('successName').innerHTML = await message;
        })
        .catch(error => {
            console.error("인증번호 요청 실패", error);

            error.text().then(message => {
                vibrateTarget(nicknameModal);
                errorNickname.innerHTML = message;
            });
        });

}

function checkInputLength() {
    nicknameChangeButton.disabled = nicknameInput.value.length < 2;
}


// 전화번호 수정
const newPhoneNumCon = document.getElementById('newPhoneNumCon');
const newPhoneInput = document.getElementById('newPhone');
const newPhoneEraseBtn = document.getElementById('newPhoneEraseBtn');
const newPhoneAuthBtn = document.getElementById('newPhoneAuthBtn');
const authNumInputCon = document.getElementById('authNumInputCon');
const authNumInput = document.getElementById('authNumInput');
const authConfirmBtn = document.getElementById('authConfirmButton');

function openPhoneModal(modalPhone) {
    newPhoneNumCon.classList.remove('disable')
    newPhoneInput.value = "";
    newPhoneEraseBtn.classList.remove('show');
    newPhoneAuthBtn.classList.add('disable');
    authNumInputCon.classList.add('disable');
    authNumInput.value = "";
    errorAuth.innerHTML = "";
    authConfirmBtn.setAttribute("disabled", "disabled");
    stopTimer();
    openModal(modalPhone)
}

newPhoneInput.addEventListener("input", () => {
    // 삭제버튼 on
    if (newPhoneInput.value.trim() !== "") {
        newPhoneEraseBtn.classList.add('show');
    } else {
        newPhoneEraseBtn.classList.remove('show');
    }
    // 010으로 시작하고 전화번호 11자리 다 채울시 인증번호 받기 버튼 활성화
    if (/^010\d{8}$/.test(newPhoneInput.value)) {
        newPhoneAuthBtn.classList.remove('disable');
    } else {
        newPhoneAuthBtn.classList.add('disable');
    }
})

// 삭제버튼 누를시 인증번호 disable 및 input값 삭제
newPhoneEraseBtn.addEventListener("click", () => {
    newPhoneEraseBtn.classList.remove('show')
    newPhoneAuthBtn.classList.add('disable');
    newPhoneInput.value = "";
})

// 인증번호 관련
newPhoneAuthBtn.addEventListener("click", () => {
    if (!newPhoneAuthBtn.classList.contains('disable')) {
        authNumInputCon.classList.remove('disable');
        newPhoneNumCon.classList.add('disable');
        newPhoneEraseBtn.classList.remove('show');
        errorAuth.innerHTML = '';
        startVerification();
    }
})

authNumInput.addEventListener("input", () => {
    let authNumValue = authNumInput.value;

    if (authNumValue.length === 6) {
        authConfirmBtn.removeAttribute('disabled');
    } else {
        authConfirmBtn.setAttribute('disabled', "true");
    }
})

function startVerification() {
    let tel = document.getElementById("newPhone").value;

    fetch("/mypage/authenticateTelStart?tel=" + tel, {
        method: "POST",
    })
        .then(function (response) {
            if (!response.ok) {
                showGlobalDialogue("서버와의 통신에 오류가 있습니다.<br>나중에 다시 시도해 주세요.")
                throw new Error("서버 통신 오류");
            }
            startTimer();
        })
        .catch(function (error) {
            console.error("인증번호 요청 실패", error);
            showGlobalDialogue("오류가 발생했습니다.<br>다시 시도해주세요.")
        });

    newPhoneAuthBtn.classList.add('disable');
}

// 인증번호 전송 로직
function authenticate() {
    let tel = document.getElementById("newPhone").value;
    let code = document.getElementById("authNumInput").value;

    fetch("/mypage/authenticateTel?tel=" + tel + "&code=" + code, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("서버 통신 오류");
            }
        })
        .then(async message => {
            stopTimer();
            closeModal();

            document.getElementById('phoneCon').value = tel
            document.getElementById('successPhone').innerHTML = await message;
        })
        .catch(function (error) {
            console.error("인증 확인 실패", error);

            vibrateTarget(phoneModal);
            errorAuth.innerHTML = "인증에 실패했습니다. 올바른 값을 입력해주세요."
        });
}

let verificationTimer;
const timerDuration = 60; // 디폴트 타이머 시간
const timerContainer = document.getElementById('timerContainer');
const timerText = document.getElementById('timerText');

function startTimer() {
    let timer = timerDuration; // 작동 타이머

    function updateTimer() {
        timer--;

        let minutes = Math.floor(timer / 60);
        let seconds = timer % 60;

        timerText.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;

        if (timer < 0) {
            handleTimerExpiration();
        }
    }

    updateTimer();

    verificationTimer = setInterval(updateTimer, 1000);
}

function stopTimer() {
    clearInterval(verificationTimer);
    timerText.innerHTML = '';
}

function handleTimerExpiration() {
    errorAuth.innerHTML = "입력시간이 초과되었습니다. 다시 시도해주세요."
    authConfirmBtn.classList.add('disable')
    stopTimer();
    newPhoneNumCon.classList.remove('disable');
    newPhoneAuthBtn.classList.remove('disable');
    authNumInput.value = '';
    authNumInputCon.classList.add('disable');
}

// 비밀번호 수정관련
const pwChangeConfirmBtn = document.getElementById('pwChangeConfirm');

function isValidPassword(password) {
    // 최소 6자, 최대 15자, 영문, 숫자, 특수문자 2가지 이상 포함
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{6,15}$/;

    return passwordRegex.test(password);
}

function confirmChangingPassword() {
    let password = document.getElementById('newPw').value;
    let passwordCheck = document.getElementById('newPwCheck').value;

    // 수정하고자 하는 비밀번호가 조건에 맞지 않을 시(혹은 없을 시)
    if (!isValidPassword(password)) {
        showGlobalDialogue("비밀번호를 확인해주세요.")
        return;
    }
    // 비밀번호 확인이 일치하지 않을 경우
    if (password !== passwordCheck) {
        showGlobalDialogue("비밀번호와 비밀번호 확인이<br>일치하지 않습니다.")
        return;
    }

    let newPassword = document.getElementById("newPw").value;
    let newPasswordCheck = document.getElementById("newPwCheck").value;

    let form = document.getElementById("changePasswordForm");
    form.querySelector('[name="password"]').value = newPassword;
    form.querySelector('[name="passwordCheck"]').value = newPasswordCheck;

    form.submit();
}


///////////////////////////////////////////////////////////////////////
nicknameInput.addEventListener('keyup', function () {
    checkInputLength();
});

nicknameChangeButton.addEventListener('click', function () {
    let newNickname = nicknameInput.value;
    
    changeNickname(newNickname);
})
