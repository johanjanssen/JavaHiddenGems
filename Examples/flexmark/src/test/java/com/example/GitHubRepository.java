package com.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
class GitHubRepository {
    private String html_url;
    private String pushed_at;
    private String updated_at;
    private String stargazers_count;
    private String watchers_count;

    public GitHubRepository() {
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getPushed_at() {
        return pushed_at;
    }

    public void setPushed_at(String pushed_at) {
        this.pushed_at = pushed_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(String stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(String watchers_count) {
        this.watchers_count = watchers_count;
    }

    @Override
    public String toString() {
        ZonedDateTime lastPushedDateTime = ZonedDateTime.parse(pushed_at);
        String lastPush = " last push: " + lastPushedDateTime.getDayOfYear() + "-" + lastPushedDateTime.getMonth().toString().toLowerCase(Locale.ROOT) + "-" + lastPushedDateTime.getYear();

        String longAgo = "";
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime oneYearAgo = now.minusYears(1);
        ZonedDateTime twoYearsAgo = now.minusYears(2);
        if (lastPushedDateTime.isBefore(twoYearsAgo)) {
            longAgo = " !! No commit for more than 2 years !!";
        } else if (lastPushedDateTime.isBefore(oneYearAgo)) {
            longAgo = " !! No commit for more than 1 year !!";
        }

        return html_url + lastPush + " stargazers: " + stargazers_count + " watchers: " + watchers_count + longAgo;
    }
}
