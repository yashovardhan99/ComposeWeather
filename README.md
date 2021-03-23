# Compose Weather

![Workflow result](https://github.com/yashovardhan99/composeweather/workflows/Check/badge.svg)

## :scroll: Description

Compose Weather is a simple weather app built to demonstrate the simplicity and ease of use for
Jetpack Compose.

Currently, it uses a dummy data source but can be easily configured to use a remote API.

## :bulb: Motivation and Context

I was really excited for this challenge since I was starting to get my hands at graphics with
Compose.

There are 5 different weather icons to represent different weather conditions. All are custom-made
by me using Compose paths with support for animations. Every time you refresh the app, you will find
a new weather icon animating in its place!

This is something I really worked hard on. Even getting the cubic bezier curves to animate properly
felt really great. :sparkles:

Apart from that, this app demonstrates the ability to customize compose so easily!
Each image the app loads is associated with a color and the entire theme changes to support that
weather. This change is seamless thanks to Jetpack Compose's simple theme functionality. Further,
there are multiple images (currently hardcoded links) for each weather condition. This can later be
customized to each city's individual tastes so that when it snows in Paris, the app actually shows
Paris covered in Snow :snowflake:

The app displays a simple linear gradient over the background image to give it the fade effect. This
allows to easily make text readable without affecting the look and feel of the app.

Further, the app is fully accessible :wheelchair: , and includes (and excludes) content descriptions
when needed.

<!--- Optionally point readers to interesting parts of your submission. -->
<!--- What are you especially proud of? -->

## :camera_flash: Screenshots

<!-- You can add more screenshots here if you like -->
<img src="/results/screenshot_1.png" width="260">&nbsp;
<img src="/results/screenshot_2.png" width="260">&nbsp;
<img src="/results/screenshot_3.png" width="260">&nbsp;
<img src="/results/screenshot_4.png" width="260">&nbsp;
<img src="/results/screenshot_5.png" width="260">&nbsp;

# :computer: Animations

All weather icons used in the app are animated using Compose (without any external tools)
<img src="/results/animated_1.gif" width="260">&nbsp;
<img src="/results/animated_2.gif" width="260">&nbsp;
<img src="/results/animated_3.gif" width="260">&nbsp;
<img src="/results/animated_4.gif" width="260">&nbsp;
<img src="/results/animated_5.gif" width="260">&nbsp;

## License

```
Copyright 2021 Yashovardhan Dhanania

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```