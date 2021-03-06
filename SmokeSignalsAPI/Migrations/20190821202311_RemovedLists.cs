﻿using Microsoft.EntityFrameworkCore.Migrations;

namespace SmokeSignalsAPI.Migrations
{
    public partial class RemovedLists : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Users_Chats_ChatId",
                table: "Users");

            migrationBuilder.DropIndex(
                name: "IX_Users_ChatId",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "ChatId",
                table: "Users");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "ChatId",
                table: "Users",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Users_ChatId",
                table: "Users",
                column: "ChatId");

            migrationBuilder.AddForeignKey(
                name: "FK_Users_Chats_ChatId",
                table: "Users",
                column: "ChatId",
                principalTable: "Chats",
                principalColumn: "ChatId",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
