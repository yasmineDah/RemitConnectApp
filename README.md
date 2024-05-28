# design decisions and challenges

- I had one primary challenge, which was the navigation with Compose. What made it hard is
  displaying the bottom sheets. When I tried to add them as routes to the navigation graph
  RemitConnectNavHost, they got displayed in a new screen without overlaying the existing one. I
  looked for it online but it seemed a little complicated and I wanted to deliver the assignment in
  a reasonable timeframe.

- Dependency injection framework : I was hesitating between Koin and Hilt. As it is a simple App, it
  seemed to me that using koin is okay because Koin injects dependencies at runtime and that would
  slow down the user experience, but that would result in a significant bad experience if the app is
  too complicated. But even though, I chose to use Hilt because I expected you are using it also so
  just to facilitate the review process.

# Additional notes

- There was a confusion about the APIs, the recipients API doesn't return the phone number of the
  user, so I preferred to stock the recipients data locally, but even thought I added the necessary
  code to fetch the API

- Same for countries API, the dialCode and the flag of the country is not returned and I need them
  to give more informative UI. So, I have added a countries json file and fetched the countries from
  there

- I have added a simple unit tests to make sure that SendMoneyScreen is working as expected (
  SendMoneyViewModelTest)

- I think to convert the money correctly to the recipient's currency, we need to sync between the
  recipients and countries APIs. But because in all cases I will convert the money using a fixed
  conversion rate (1 euro = 655,94 CFA), plus the confusion I had, that won't be really possible. I
  hope that won't affect my application :)
