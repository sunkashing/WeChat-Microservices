var profileDict = {};

// alert box with profiles (consider jquery dialog or bPopup)
function displayProfile(username) {
    alert(
        '👤Profile details\n'
        + '▬▬▬▬▬▬▬▬▬⛅▬▬▬▬▬▬▬▬▬\n'
        + '📛Name: ' + profileDict[username].name + '\n'
        + '👪Gender: ' + profileDict[username].gender + '\n'
        + '🔞Age: ' + profileDict[username].age + '\n'
        + '▬▬▬▬▬▬▬▬▬🙃▬▬▬▬▬▬▬▬▬\n'
    );

}

$(document).ready(function () {

    var userParam = getUrlParameter('username');
    var headers = {
        username: userParam
    };

    function updateAvatarDict() {
        // use while loop to guarantee the request is finished
        while (Object.keys(profileDict).length == 0) {
            $.get("/getallprofiles", {}, function (response) {
                for(var i = 0; i < response.length; i++) {
                    var profile = response[i];
                    profileDict[profile.username] = profile;
                }
            });
        }
    }
    // turn async off to guarantee the request is finished
    $.ajaxSetup({async:false});
    updateAvatarDict();
    $.ajaxSetup({async:true});

    function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
    }

    var stompClient = null;

    connect(function () {
        console.log("Connected!");
    });

    function connect(callback) {
        $('.alert').hide();
        var socket = new SockJS('/redis-chat');
        stompClient = Stomp.over(socket);

        stompClient.connect(headers, function (frame) {
            // setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/messages', function (chatMessage) {
                console.log("Message: ", chatMessage);
                showChatMessage(JSON.parse(chatMessage.body));
            });
            callback();
        }, function (message) {
            disconnect();
            $('.alert').show();
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        // setConnected(false);
        console.log("Disconnected");
    }

    function showChatMessage(chatMessage) {
        if (chatMessage.username == userParam) {
            insertChat("me", chatMessage.message, chatMessage.username, chatMessage.time)
        }else {
            insertChat("you", chatMessage.message, chatMessage.username, chatMessage.time)
        }
    }


    function getAllChatMessages() {
        $.get("/getallmessages", {}, function (response) {
            for(var i = 0; i < response.length; i++) {
                var message = response[i];
                showChatMessage(message);
            }
        });
    }

    getAllChatMessages();

    function formatAMPM(date) {
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var ampm = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12;
        hours = hours ? hours : 12; // the hour '0' should be '12'
        minutes = minutes < 10 ? '0' + minutes : minutes;
        var strTime = hours + ':' + minutes + ' ' + ampm;
        return strTime;
    }

    $(".mytext").on("keydown", function (e) {
        if (e.which == 13) {
            var text = $(this).val();
            if (text !== "" && userParam in profileDict) {
                sendMessage();
                $(this).val('');
            }
        }
    });

    $("#send-msg").click(function () {
        $(".mytext").trigger({type: 'keydown', which: 13, keyCode: 13});
    });

    function insertChat(who, text, username, time) {
        var control = "";

        if (who == "you") {
            control = '<li style="width:100%">' +
                '<div class="msj macro">' +
                '<div class="avatar"><img class="img-circle" style="width:50px; height: 50px" src="' + profileDict[username].imgURL + '" /></div>' +
                '<div class="text text-l">' +
                '<p>' + text + '</p>' +
                '<p><small>' + time + '</small></p>' +
                '</div>' +
                '</div>' +
                '</li>';
        } else {
            control = '<li style="width:100%;">' +
                '<div class="msj-rta macro">' +
                '<div class="text text-r">' +
                '<p>' + text + '</p>' +
                '<p><small>' + time + '</small></p>' +
                '</div>' +
                '<div class="avatar" style="padding:0px 0px 0px 10px !important"><img class="img-circle" style="width: 50px; height: 50px" src="' + profileDict[username].imgURL + '" /></div>' +
                '</li>';
        }
        $("#chat-room").append(control).scrollTop($("#chat-room").prop('scrollHeight'));
    }

    function sendMessage() {
        headers['time'] = formatAMPM(new Date());
        if (stompClient !== null && stompClient.connected) {
            var chatMessage = $("#chat-message").val();
            stompClient.send("/app/message", headers, chatMessage);
            $("#chat-message").val("");
        } else {
            connect(function () {
                var chatMessage = $("#chat-message").val();
                stompClient.send("/app/message", headers, chatMessage);
                $("#chat-message").val("");
            });
        }
    }

    // insert all the profiles
    function insertProfile(profile) {
        var profileHtml = '<li>' +
            '<div id="my-profile" style="margin-top: 3px; margin-left: 3px">' +
            '<img class="img-circle" style="width:30px; height: 30px" src="' + profile.imgURL + '" />' +
            '<button id="profile-button" onclick="displayProfile(' + "'" + profile.username + "'" + ')">' + profile.name +'</button>' +
            '</div>' +
            '</li>';
        $('#profile-drop-down').prepend(profileHtml);
    }

    function getAllProfiles() {
        for (var key in profileDict) {
            insertProfile(profileDict[key])
        }
    }

    getAllProfiles();

    // search box for group members
    $("#myInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $(".dropdown-menu li").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});

