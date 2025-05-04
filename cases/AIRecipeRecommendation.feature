Feature: AI-assisted recipe recommendation using LLM

  Scenario: Recommend the best recipe based on preferences using AI
    Given the user has the following preferences:
      | Dietary Restrictions | Vegan                             |
      | Ingredients          | Tomatoes, basil, pasta, olive oil |
      | Time Available       | 30 minutes                        |
    And the following recipe database is available:
      | Name                      | Ingredients                         | Time        | Dietary |
      | Spaghetti with Tomato Sauce | Tomatoes, pasta, basil, olive oil | 25 minutes  | Vegan   |
      | Tomato Basil Soup         | Tomatoes, basil, garlic             | 40 minutes  | Vegan   |
      | Vegan Pesto Pasta         | Basil, pasta, olive oil, garlic     | 20 minutes  | Vegan   |
    When the AI generates a recipe recommendation
    Then the suggested recipe should be "Spaghetti with Tomato Sauce"
    And the explanation should include "uses all of the user's available ingredients"
