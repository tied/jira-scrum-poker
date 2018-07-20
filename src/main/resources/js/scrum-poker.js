/*!
 * JavaScript implementation for the client side of the Scrum Poker plugin.
 */
(function(ScrumPoker, $, undefined) {

    var uri = AJS.contextPath() + '/rest/scrumpoker/1.0';
    var refreshTimeout;
    var redirectTimeout;
    var redirectTimeoutActive = false;
    var fadeReferencesTimeout;
    var fadeReferencesTimeoutActive = false;
    var lastShownEstimation;

    /* Initialize the automatic refresh of the Scrum Poker session every 2 seconds. */
    ScrumPoker.poll = function(issueKey) {
        refreshSession(issueKey);
        refreshTimeout = setTimeout(function() {
            ScrumPoker.poll(issueKey);
        }, 2000);
    }

    /* Refresh the Scrum Poker session and display results using Mustache template */
    function refreshSession(issueKey) {
        $.get(uri + '/session/' + issueKey, function(data) {
            if(data.confirmedVoteExists) {
                 $('#scrum-poker-cards').empty();
                 $('#scrum-poker-participants').empty();
                 $('#scrum-poker-buttons').empty();
                 $('#scrum-poker-finished').html(Mustache.render($('#scrum-poker-finished-template').html(), data));
                scheduleRedirectToIssue(issueKey);
            } else {
                $('#scrum-poker-cards').html(Mustache.render($('#scrum-poker-cards-template').html(), data));
                $('#scrum-poker-participants').html(Mustache.render($('#scrum-poker-participants-template').html(), data));
                $('#scrum-poker-buttons').html(Mustache.render($('#scrum-poker-buttons-template').html(), data));
                $('#scrum-poker-finished').empty();
                cancelRedirectToIssue();
            }
        }).fail(function() {
            scheduleRedirectToIssue(issueKey);
        });
    }

    /* Schedule a redirect to the issue page after 60 seconds */
    function scheduleRedirectToIssue(issueKey) {
        if (!redirectTimeoutActive) {
            redirectTimeout = setTimeout(function() {
                clearTimeout(refreshTimeout);
                window.location.href = AJS.contextPath() + '/browse/' + issueKey;
            }, 60000);
            redirectTimeoutActive = true;
        }
    }

    /* Cancel the redirect to the issue page */
    function cancelRedirectToIssue() {
        if (redirectTimeoutActive) {
            clearTimeout(redirectTimeout);
            redirectTimeoutActive = false;
        }
    }

    /* Update Scrum Poker session and add or change a vote for the current user */
    ScrumPoker.updateSession = function(issueKey, chosenCard) {
        $.post(uri + '/session/' + issueKey + '/card/' + encodeURIComponent(chosenCard), function(data, status) {
            refreshSession(issueKey);
        });
    }

    /* Reveal all votes giving by participants of this Scrum Poker session */
    ScrumPoker.revealSession = function(issueKey) {
        $.post(uri + '/session/' + issueKey + '/reveal', function(data, status) {
            refreshSession(issueKey);
        });
    }

    /* Reset the Scrum Poker session allowing for a new round for the current issue */
    ScrumPoker.resetSession = function(issueKey) {
        $.post(uri + '/session/' + issueKey + '/reset', function(data, status) {
            refreshSession(issueKey);
        });
    }

    /* Confirm the estimation for a Scrum Poker session ending itself and persisting the estimation on the issue */
    ScrumPoker.confirmSession = function(issueKey, estimation) {
        $.post(uri + '/session/' + issueKey + '/confirm/' + encodeURIComponent(estimation), function(data, status) {
            refreshSession(issueKey);
        });
    }

    /* Loads reference estimations that can be used to decide for or against an estimation */
    ScrumPoker.showReferences = function(estimation) {
        if(typeof estimation !== 'undefined' && $.isNumeric(estimation))  {
            clearTimeout(fadeReferencesTimeout);
            fadeReferencesTimeoutActive = false;
            if (estimation != lastShownEstimation || $('#scrum-poker-references').is(":empty")) {
                $.get(uri + '/session/reference/' + encodeURIComponent(estimation), function(data, status) {
                    $('#scrum-poker-references').html(Mustache.render($('#scrum-poker-references-template').html(), data));
                    lastShownEstimation = estimation;
                }, 'json');
            }
            $('#scrum-poker-references').fadeIn(1000);
        } else {
            if (!fadeReferencesTimeoutActive) {
                fadeReferencesTimeout = setTimeout(function() {
                    $('#scrum-poker-references').fadeOut(1000);
                }, 2000);
                fadeReferencesTimeoutActive = true;
            }
        }
    }

}(window.ScrumPoker = window.ScrumPoker || {}, jQuery));
