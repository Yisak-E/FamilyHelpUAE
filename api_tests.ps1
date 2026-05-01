# Bypass SSL validation for local testing
[System.Net.ServicePointManager]::ServerCertificateValidationCallback = { $true }

# Base URL for the API
$baseUrl = "https://localhost:8443"

# Headers for JSON content
$headers = @{
    "Content-Type" = "application/json"
}

# --- Auth Controller ---

# Signup
$random = Get-Random
$signupBody = @{
    firstName = "John"
    lastName  = "Doe"
    email     = "john.doe$random@example.com"
    password  = "Password123"
    role      = "USER"
    familyName= "Doe Family $random"
    address   = "123 Main St"
    familySize= 4
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/api/auth/signup" -Method Post -Body $signupBody -Headers $headers

# Login
$loginBody = @{
    email    = "john.doe$random@example.com"
    password = "Password123"
} | ConvertTo-Json

$loginResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/login" -Method Post -Body $loginBody -Headers $headers
$token = $loginResponse.token

# Add token to headers for subsequent requests
$authHeaders = $headers.Clone()
$authHeaders.Add("Authorization", "Bearer $token")

# --- Help Controller ---

# Create Post
$createPostDto = @{
    postType    = "SEEK"
    title       = "Need help with groceries"
    category    = "shopping"
    description = "I need someone to help me with grocery shopping this weekend."
    urgency     = "high"
    neededBy    = "2026-05-03T10:00:00"
} | ConvertTo-Json

$createdPostResponse = Invoke-RestMethod -Uri "$baseUrl/api/help/posts" -Method Post -Body $createPostDto -Headers $authHeaders
$createdPost = $createdPostResponse[0]
$postId = $createdPost.id
Write-Output "Created Post ID: $postId"

# Get Posts
Invoke-RestMethod -Uri "$baseUrl/api/help/posts?type=SEEK" -Method Get -Headers $authHeaders

# Get My Activities
Invoke-RestMethod -Uri "$baseUrl/api/help/my-activity" -Method Get -Headers $authHeaders

# --- Application Controller ---

# Apply to Post
Invoke-RestMethod -Uri "$baseUrl/api/applications/apply/$postId" -Method Post -Headers $authHeaders

# Get Applicants for Post
$applications = Invoke-RestMethod -Uri "$baseUrl/api/applications/post/$postId" -Method Get -Headers $authHeaders
$applicationId = $applications[0].id

# Accept Application
Invoke-RestMethod -Uri "$baseUrl/api/applications/$applicationId/accept" -Method Patch -Headers $authHeaders

# Cancel Application
Invoke-RestMethod -Uri "$baseUrl/api/applications/$applicationId/cancel" -Method Delete -Headers $authHeaders

# --- Calendar Controller ---

# Get My Calendar
Invoke-RestMethod -Uri "$baseUrl/api/calendar/weekly" -Method Get -Headers $authHeaders

# --- Feedback Controller ---

# Submit Feedback
$feedbackBody = @{
    rating      = 5
    comment     = "Great help!"
    anonymous   = $false
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/api/feedback/submit/$postId" -Method Post -Body $feedbackBody -Headers $authHeaders

# --- Reward Controller ---

# Get My Rewards
Invoke-RestMethod -Uri "$baseUrl/api/rewards/getMine/1" -Method Get -Headers $authHeaders # Assuming user with id 1

# Get Leaderboard
Invoke-RestMethod -Uri "$baseUrl/api/rewards/leaderboard" -Method Get -Headers $authHeaders

# --- Task Controller ---

# Create Task
$createTaskDto = @{
    postType    = "OFFER"
    title       = "Offering to walk dogs"
    category    = "pets"
    description = "I can walk your dog for free."
    availability= "weekends"
} | ConvertTo-Json

$createdTaskResponse = Invoke-RestMethod -Uri "$baseUrl/api/help/posts" -Method Post -Body $createTaskDto -Headers $authHeaders
$createdTask = $createdTaskResponse[0]
$taskId = $createdTask.id
Write-Output "Created Task ID: $taskId"

# Get Community Feed
Invoke-RestMethod -Uri "$baseUrl/api/help/posts" -Method Get -Headers $authHeaders

# Get Task By Id
Invoke-RestMethod -Uri "$baseUrl/api/help/posts/$taskId" -Method Get -Headers $authHeaders

# Complete Task
Invoke-RestMethod -Uri "$baseUrl/api/help/posts/$taskId/complete" -Method Patch -Headers $authHeaders

# --- Actuator ---

# Get Actuator Links
Invoke-RestMethod -Uri "$baseUrl/actuator" -Method Get

# Get Actuator Health
Invoke-RestMethod -Uri "$baseUrl/actuator/health" -Method Get
